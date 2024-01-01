package org.kyotskin.trivia
package domain

type AnswerText = String
type Questions = List[Question]

case class Question(
  text: String,
  possibleAnswers: List[AnswerText],
  correctAnswer: AnswerText
)