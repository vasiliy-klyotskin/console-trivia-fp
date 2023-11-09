package org.kyotskin.trivia
package presentation

import domain.*

def greetingStep(): GreetingStepViewModel = {
  val greeting = "Welcome to the Console Trivia Game!\n\nGet ready to test your knowledge and have a blast with our trivia questions. Whether you're a seasoned trivia pro or just looking for some fun, this game is perfect for everyone. Answer questions from a variety of categories, rack up points, and compete with friends or challenge yourself.\n\nLet's see how much you know and how well you can think on your feet. Are you ready to embark on this thrilling trivia adventure? Good luck and have a fantastic time!"
  val nameRequest = "Before we begin, please enter your name so we can personalize your experience and keep track of your score. Type your name and press Enter to get started."
  GreetingStepViewModel(greeting, nameRequest)
}

def mapToCategoryChoiceViewModel(categories: List[Category]): CategoryChoiceStepViewModel = {
  val message = "Great! Now that we know your name, it's time to choose the categories you'd like to play. You can select from a variety of categories. Please enter the category numbers you'd like to include, separated by spaces, or simply type 'all' to play with questions from all categories. Let's get started!"
  CategoryChoiceStepViewModel(message, indexedList(categories.map(_.name)))
}

def mapToDifficultyChoiceStepViewModel(playerName: String, difficulties: List[Difficulty]): DifficultyChoiceStepViewModel = {
  val message = s"Fantastic, $playerName! You've selected your categories. Now, it's time to choose the difficulty level for your trivia adventure. Please enter the number corresponding to your desired difficulty level:"
  DifficultyChoiceStepViewModel(message, indexedList(difficulties.map(mapDifficultyName)))
}

private def mapDifficultyName(difficulty: Difficulty): String = {
  difficulty match {
    case Difficulty.Easy => "Easy"
    case Difficulty.Medium => "Medium"
    case Difficulty.Hard => "Hard"
  }
}

def mapToStatusViewModel(trivia: Trivia): StatusViewModel = {
  val score = fullScore(trivia.answers, trivia.difficulty)
  val progressTitle = s"Progress:   ${trivia.answers.length} / ${trivia.questions.length}"
  val scoreTitle = s"Score:      $score"
  val progressIndicator = trivia
    .answers
    .foldLeft("")((acc, ans) => acc + (if ans.isCorrect then "+ " else "o "))
    .trim
  StatusViewModel(progressTitle, scoreTitle, progressIndicator)
}

def mapToQuestionViewModel(question: Question): QuestionViewModel = {
  QuestionViewModel(question.text, indexedList(question.possibleAnswers))
}

private def indexedList(strings: List[String]): List[String] = {
  strings.zipWithIndex.map((str, index) => s"${index+1}. $str")
}