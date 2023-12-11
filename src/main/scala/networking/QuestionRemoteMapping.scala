package org.kyotskin.trivia
package networking
import domain.Question
import cats.effect.IO

def map(remote: QuestionRemoteDTO, shuffle: List[String] => IO[List[String]]): IO[Question] = {
  val allAnswers = remote.incorrectAnswers.appended(remote.correctAnswer)
  shuffle(allAnswers).map( shuffledAnswers =>
    Question(
      text = remote.question.text,
      possibleAnswers = shuffledAnswers,
      correctAnswer = remote.correctAnswer
    )
  )
}
