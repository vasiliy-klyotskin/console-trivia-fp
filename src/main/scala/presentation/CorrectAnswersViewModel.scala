package org.kyotskin.trivia
package presentation

case class CorrectAnswersViewModel(
  items: List[CorrectAnswersViewModelItem]
)

case class CorrectAnswersViewModelItem(
  question: String,
  correctAnswer: String
)