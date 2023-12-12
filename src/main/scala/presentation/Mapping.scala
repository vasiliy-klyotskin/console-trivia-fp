package org.kyotskin.trivia
package presentation

import domain.*

def greetingViewModel(): GreetingViewModel = {
  val greeting = "Welcome to the Console Trivia Game!\n\nGet ready to test your knowledge and have a blast with our trivia questions. Whether you're a seasoned trivia pro or just looking for some fun, this game is perfect for everyone. Answer questions from a variety of categories, rack up points, and compete with friends or challenge yourself.\n\nLet's see how much you know and how well you can think on your feet. Are you ready to embark on this thrilling trivia adventure? Good luck and have a fantastic time!"
  val nameRequest = "Before we begin, please enter your name so we can personalize your experience and keep track of your score. Type your name and press Enter to get started."
  GreetingViewModel(greeting, nameRequest)
}

def mapToCategoryChoiceViewModel(categories: List[Category]): CategoryChoiceViewModel = {
  val message = "Great! Now that we know your name, it's time to choose the categories you'd like to play. You can select from a variety of categories. Please enter the category numbers you'd like to include, separated by spaces, or simply type 'all' to play with questions from all categories. Let's get started!"
  CategoryChoiceViewModel(message, indexedList(categories.map(_.name)))
}

def mapToDifficultyChoiceViewModel(playerName: String, difficulties: List[Difficulty]): DifficultyChoiceViewModel = {
  val message = s"Fantastic, $playerName! You've selected your categories. Now, it's time to choose the difficulty level for your trivia adventure. Please enter the number corresponding to your desired difficulty level:"
  DifficultyChoiceViewModel(message, indexedList(difficulties.map(mapDifficultyName)))
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

def mapToTriviaResultsViewModel(trivia: Trivia, player: String): TriviaResultsViewModel = {
  if (trivia.questions.isEmpty) {
    TriviaResultsViewModel(title = "", metrics = List.empty, feedback = "", farewellMessage = "")
  } else {
    val correctAnswers = trivia.answers.count(_.isCorrect)
    val questionsCount = trivia.questions.length
    val title = s"Thank you, $player, for playing the Trivia Game!"
    val farewellMessage = "Hope you had a great time challenging your knowledge. Play again soon!"
    val scoreMetric = s"Your Score: ${fullScore(trivia.answers, trivia.difficulty)}"
    val accuracyMetric = s"Accuracy: $correctAnswers / $questionsCount"
    val feedback = feedbackFor(correctAnswers, questionsCount, player)
    TriviaResultsViewModel(title, List(scoreMetric, accuracyMetric), feedback, farewellMessage)
  }
}

private def feedbackFor(correctAnswers: Int, questionsCount: Int, player: String): String = {
  val percentageCorrect = (correctAnswers.toDouble / questionsCount) * 100
  if (correctAnswers == questionsCount) {
    s"Outstanding, $player! You're a trivia genius. Congratulations on achieving a remarkable score!"
  } else if (percentageCorrect >= 80) {
    s"Well done, $player! You have a solid knowledge base. Keep up the great work!"
  } else if (percentageCorrect >= 50) {
    s"Not bad, $player! You're on the right track. Keep challenging yourself to improve."
  } else {
    s"Looks like there's room for improvement, $player. Don't worry, practice makes perfect!"
  }
}

def mapToCorrectAnswersViewModel(questions: List[Question]): CorrectAnswersViewModel = {
  val presentedQuestions = indexedList(questions.map(_.text))
  val presentedAnswers = questions.map(question => s"Correct Answer: ${question.correctAnswer}")
  val items = presentedQuestions.zip(presentedAnswers).map((question, answer) => CorrectAnswersViewModelItem(question, answer))
  CorrectAnswersViewModel(items)
}

def mapToLeaderboardViewModel(players: List[FinalizedPlayer], current: FinalizedPlayer): LeaderboardViewModel = {
  val title = "Trivia Leaderboard"
  val message = "Keep playing and improving your knowledge!"
  val noOtherPlayers = !players.exists(_ != current)
  if (noOtherPlayers) {
    val yourPlace = s"Oh, it seems like there is no one other than you! Your highest score is ${current.topScore}"
    LeaderboardViewModel(title, List.empty, yourPlace, message)
  } else {
    val sortedPlayersByScore = players.sortBy(_.topScore)(Ordering[Int].reverse)
    val longestNameLength = players.maxBy(_.name.length).name.length
    val formatToItem = (player: FinalizedPlayer) => s"%-${longestNameLength}s   %d".format(player.name, player.topScore)
    val items = indexedList(sortedPlayersByScore.map(formatToItem))
    val placeNumber = sortedPlayersByScore.indexOf(current) + 1
    val yourPlace = s"Your place is $placeNumber with a score of ${current.topScore}"
    LeaderboardViewModel(title, items, yourPlace, message)
  }
}

private def indexedList(strings: List[String]): List[String] = {
  strings.zipWithIndex.map((str, index) => s"${index+1}. $str")
}