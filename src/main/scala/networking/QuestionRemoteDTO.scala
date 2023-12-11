package org.kyotskin.trivia
package networking

case class QuestionRemoteDTO(
  id: String,
  question: QuestionTextRemoteDTO,
  correctAnswer: String,
  incorrectAnswers: List[String]
)

case class QuestionTextRemoteDTO(text: String)