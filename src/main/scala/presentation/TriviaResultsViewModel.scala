package org.kyotskin.trivia
package presentation

import domain.*

case class TriviaResultsViewModel(
  title: String,
  metrics: List[String],
  feedback: String,
  farewellMessage: String
)

def mapToTriviaResultsViewModel(trivia: Trivia, playerName: PlayerName): TriviaResultsViewModel = {
  if (trivia.questions.isEmpty) {
    TriviaResultsViewModel(title = "", metrics = List.empty, feedback = "", farewellMessage = "")
  } else {
    val correctAnswers = trivia.answers.count(_.isCorrect)
    val questionsCount = trivia.questions.length
    val title = s"Thank you, $playerName, for playing the Trivia Game!"
    val farewellMessage = "Hope you had a great time challenging your knowledge. Play again soon!"
    val scoreMetric = s"Your Score: ${fullScore(trivia.answers, trivia.difficulty)}"
    val accuracyMetric = s"Accuracy: $correctAnswers / $questionsCount"
    val feedback = feedbackFor(correctAnswers, questionsCount, playerName)
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