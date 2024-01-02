package org.kyotskin.trivia
package composition

import cats.effect.IO
import domain.*
import presentation.*
import ui.*
import networking.*

def composeTrivia(ui: UI, dataAccess: DataAccess): IO[Unit] = {
  for {
    _ <- greetingsStep(ui)
    categoryChoice <- categoryStep(ui)
    difficulty <- difficultyStep(ui)
    questions <- loadQuestionsStep(ui, dataAccess, difficulty, categoryChoice)
    completedTrivia <- triviaStep(ui, dataAccess, questions, difficulty)
    _ <- gameResultsStep(ui, completedTrivia)
    _ <- composeTrivia(ui, dataAccess)
  } yield ()
}

// MARK: Greetings Step

def greetingsStep(ui: UI): IO[Unit] = {
  for {
    _ <- ui.clear
    _ <- ui.display(greetingViewModel.textItem())
    _ <- ui.input
  } yield ()
}

// MARK: Categories Step

def categoryStep(ui: UI): IO[CategoryChoice] = {
  for {
    _ <- ui.clear
    _ <- ui.display(allCategoriesViewModel.textItem())
    categoryChoice <- requestCategoriesInput(ui)
  } yield categoryChoice
}

def requestCategoriesInput(ui: UI): IO[CategoryChoice] = {
  val tryRequestAgain = ui.display(categoryInputErrorViewModel.textItem()) >> requestCategoriesInput(ui)
  for {
    categoryChoiceResult <- ui.input.map(validateCategory(allCategories()))
    categoryChoice <- categoryChoiceResult.fold(tryRequestAgain)(IO.pure)
  } yield categoryChoice
}

// MARK: Difficulty Step

def difficultyStep(ui: UI): IO[Difficulty] = {
  for {
    _ <- ui.clear
    _ <- ui.display(allDifficultiesViewModel.textItem())
    difficulty <- requestDifficultyInput(ui)
  } yield difficulty
}

def requestDifficultyInput(ui: UI): IO[Difficulty] = {
  val tryRequestAgain = ui.display(difficultyChoiceErrorViewModel.textItem()) >> requestDifficultyInput(ui)
  for {
    difficultyResult <- ui.input.map(validateDifficulty)
    difficulty <- difficultyResult.fold(tryRequestAgain)(IO.pure)
  } yield difficulty
}

// MARK: Load Questions Step

def loadQuestionsStep(ui: UI, dataAccess: DataAccess, difficulty: Difficulty, categoryChoice: CategoryChoice): IO[Questions] = {
  for {
    _ <- ui.clear
    _ <- ui.display(questionLoadingViewModel.textItem())
    questions <- fetchQuestions(dataAccess, difficulty, categoryChoice)
    _ <- askUserIfHeWantsToStart(ui)
  } yield questions
}

def fetchQuestions(dataAccess: DataAccess, difficulty: Difficulty, categoryChoice: CategoryChoice): IO[Questions] = {
  for {
    questionsResult <- dataAccess.fetchQuestions(questionsUrl(difficulty, categoryChoice))
    questions <- questionsResult.fold(fetchQuestions(dataAccess, difficulty, categoryChoice))(IO.pure)
  } yield questions
}

def askUserIfHeWantsToStart(ui: UI): IO[Unit] = {
  for {
    _ <- ui.clear
    _ <- ui.display(questionsReadyViewModel.textItem())
    _ <- ui.input
  } yield ()
}

// MARK: Trivia Step

def triviaStep(ui: UI, dataAccess: DataAccess, questions: Questions, difficulty: Difficulty): IO[Trivia] = {
  val newTrivia = IO.pure(initialTrivia(questions, difficulty))
  questions.foldLeft(newTrivia)((trivia, question) => trivia.flatMap(processQuestion(ui, dataAccess, _, question)))
}

def processQuestion(ui: UI, dataAccess: DataAccess, trivia: Trivia, question: Question): IO[Trivia] = {
  val tryInputAnswerAgain = ui.display(answerInputErrorViewModel.textItem()) >> processQuestion(ui, dataAccess, trivia, question)
  for {
    _ <- ui.clear
    _ <- ui.display(mapToStatusViewModel(trivia).textItem())
    _ <- ui.display(mapToQuestionViewModel(question).textItem())
    startTime <- dataAccess.measureTime
    answer <- requestAnswerInput(ui, dataAccess, startTime, question)
    updatedTrivia = Trivia(trivia.questions, trivia.answers.appended(answer), trivia.difficulty)
  } yield updatedTrivia
}

def requestAnswerInput(ui: UI, dataAccess: DataAccess, startTime: Double, question: Question): IO[Answer] = {
  val tryInputAnswerAgain = ui.display(answerInputErrorViewModel.textItem()) >> requestAnswerInput(ui, dataAccess, startTime, question)
  for {
    input <- ui.input
    endTime <- dataAccess.measureTime
    answerInputResult = validateAnswer(question, endTime - startTime)(input)
    answer <- answerInputResult.fold(tryInputAnswerAgain)(IO.pure)
  } yield answer
}

// MARK: Game Result Step

def gameResultsStep(ui: UI, completedTrivia: Trivia): IO[Unit] = {
  for {
    _ <- ui.clear
    _ <- ui.display(mapToTriviaResultsViewModel(completedTrivia).textItem())
    _ <- ui.input
  } yield ()
}