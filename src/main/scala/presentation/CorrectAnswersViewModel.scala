package org.kyotskin.trivia
package presentation

import domain.Question

case class CorrectAnswersViewModel(
  items: List[CorrectAnswersViewModelItem]
)

case class CorrectAnswersViewModelItem(question: String, correctAnswer: String)

def mapToCorrectAnswersViewModel(questions: List[Question]): CorrectAnswersViewModel = {
  val presentedQuestions = indexedList(questions.map(_.text))
  val presentedAnswers = questions.map(question => s"Correct Answer: ${question.correctAnswer}")
  val items = presentedQuestions.zip(presentedAnswers).map((question, answer) => CorrectAnswersViewModelItem(question, answer))
  CorrectAnswersViewModel(items)
}