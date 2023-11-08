package org.kyotskin.trivia
package presentation

import domain.Trivia
import domain.fullScore

def mapToStatusViewModel(trivia: Trivia): StatusViewModel = {
  val score = fullScore(trivia.answers, trivia.difficulty)
  val progressTitle =     s"Progress:   0 / 0"
  val scoreTitle =        s"Score:      0"
  val progressIndicator = s""
  StatusViewModel(progressTitle, scoreTitle, progressIndicator)
}