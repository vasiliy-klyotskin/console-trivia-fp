package org.kyotskin.trivia
package composition

import cats.effect.IO
import domain.*
import presentation.*
import ui.*
import networking.*

trait UI {
  def display(textItem: TextItem): IO[Unit]
  def clear: IO[Unit]
  def input: IO[String]
}

trait DataAccess {
  def fetchQuestions(url: String): IO[Option[Questions]]
}

def composeTrivia(ui: UI, dataAccess: DataAccess): IO[Unit] = {
  for {
    playerName <- greetingsStep(ui)
    categoryChoice <- categoryStep(ui)
    difficulty <- difficultyStep(ui, playerName)
    _ <- loadQuestionsStep(ui, dataAccess, difficulty, categoryChoice)
  } yield ()
}

def greetingsStep(ui: UI): IO[PlayerName] = {
  for {
    _ <- ui.display(greetingViewModel.textItem())
    result <- requestPlayerName(ui)
    _ <- ui.clear
  } yield result
}

def requestPlayerName(ui: UI): IO[PlayerName] = {
  val tryRequestAgain = ui.display(nameInputErrorViewModel.textItem()) >> requestPlayerName(ui)
  for {
    playerName <- ui.input.map(validateName)
    result <- playerName.fold(tryRequestAgain)(IO.pure)
  } yield result
}

def categoryStep(ui: UI): IO[CategoryChoice] = {
  for {
    _ <- ui.display(allCategoriesViewModel.textItem())
    categoryChoice <- requestCategoriesInput(ui)
    _ <- ui.clear
  } yield categoryChoice
}

def requestCategoriesInput(ui: UI): IO[CategoryChoice] = {
  val tryRequestAgain = ui.display(categoryInputErrorViewModel.textItem()) >> requestCategoriesInput(ui)
  for {
    categoryChoiceResult <- ui.input.map(validateCategory(allCategories()))
    categoryChoice <- categoryChoiceResult.fold(tryRequestAgain)(IO.pure)
  } yield categoryChoice
}

def difficultyStep(ui: UI, playerName: PlayerName): IO[Difficulty] = {
  for {
    _ <- ui.display(allDifficultiesViewModel(playerName).textItem())
    difficulty <- requestDifficultyInput(ui)
    _ <- ui.clear
  } yield difficulty
}

def requestDifficultyInput(ui: UI): IO[Difficulty] = {
  val tryRequestAgain = ui.display(difficultyChoiceErrorViewModel.textItem()) >> requestDifficultyInput(ui)
  for {
    difficultyResult <- ui.input.map(validateDifficulty)
    difficulty <- difficultyResult.fold(tryRequestAgain)(IO.pure)
  } yield difficulty
}

def loadQuestionsStep(ui: UI, dataAccess: DataAccess, difficulty: Difficulty, categoryChoice: CategoryChoice): IO[Unit] = {
  for {
    _ <- ui.display(questionLoadingViewModel.textItem())
    questions <- fetchQuestions(dataAccess, difficulty, categoryChoice)
    _ <- ui.clear
    _ <- askUserIfHeWantsToStart(ui)
  } yield ()
}

def askUserIfHeWantsToStart(ui: UI): IO[Unit] = {
  for {
    _ <- ui.display(questionsReadyViewModel.textItem())
    _ <- ui.input
  } yield ()
}

def fetchQuestions(dataAccess: DataAccess, difficulty: Difficulty, categoryChoice: CategoryChoice): IO[Questions] = {
  for {
    questionsResult <- dataAccess.fetchQuestions(questionsUrl(difficulty, categoryChoice))
    questions <- questionsResult.fold(fetchQuestions(dataAccess, difficulty, categoryChoice))(IO.pure)
  } yield questions
}