package org.kyotskin.trivia
package presentation

import domain.Question

case class QuestionViewModel(
  question: String,
  possibleAnswers: List[String]
)

def mapToQuestionViewModel(question: Question): QuestionViewModel = {
  QuestionViewModel(question.text, indexedList(question.possibleAnswers))
}