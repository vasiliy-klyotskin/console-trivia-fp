package org.kyotskin.trivia
package networking

import domain.Question

case class QuestionRemoteDTO(
  id: String,
  question: QuestionTextRemoteDTO,
  correctAnswer: String,
  incorrectAnswers: List[String],
  difficulty: String,
  category: String
)

case class QuestionTextRemoteDTO(text: String)

def fromRemote(remote: QuestionRemoteDTO): Question = {
  Question(
    text = remote.question.text,
    possibleAnswers = remote.incorrectAnswers.appended(remote.correctAnswer),
    correctAnswer = remote.correctAnswer
  )
}