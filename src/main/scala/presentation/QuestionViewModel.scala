package org.kyotskin.trivia
package presentation

case class QuestionViewModel(
  question: String,
  possibleAnswers: List[String]
)
