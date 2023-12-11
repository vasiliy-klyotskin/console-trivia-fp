package org.kyotskin.trivia
package networking

case class QuestionDTO(
  id: String,
  question: QuestionTextDTO,
  correctAnswer: String,
  incorrectAnswers: List[String]
)

case class QuestionTextDTO(text: String)