package org.kyotskin.trivia
package presentation

import domain.*

def mapToStatusViewModel(trivia: Trivia): StatusViewModel = {
  val score = fullScore(trivia.answers, trivia.difficulty)
  val progressTitle = s"Progress:   ${trivia.answers.length} / ${trivia.questions.length}"
  val scoreTitle = s"Score:      $score"
  val progressIndicator = trivia
    .answers
    .foldLeft("")((acc, ans) => acc + (if ans.isCorrect then "+ " else "o "))
    .trim
  StatusViewModel(progressTitle, scoreTitle, progressIndicator)
}

def mapToQuestionViewModel(question: Question): QuestionViewModel = {
  val possibleAnswers = question
    .possibleAnswers
    .zipWithIndex
    .map((ans, index) => s"${index+1}. $ans")
  QuestionViewModel(question.text, possibleAnswers)
}