package org.kyotskin.trivia
package domain

case class Question(
  text: String,
  possibleAnswers: List[String],
  correctAnswer: String
)
