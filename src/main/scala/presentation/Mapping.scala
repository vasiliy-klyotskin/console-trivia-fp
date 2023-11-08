package org.kyotskin.trivia
package presentation

import domain.Trivia
import domain.fullScore

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