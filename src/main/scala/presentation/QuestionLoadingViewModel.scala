package org.kyotskin.trivia
package presentation

case class QuestionLoadingViewModel(text: String)

def questionLoadingViewModel: QuestionLoadingViewModel = {
  QuestionLoadingViewModel("The questions are being loaded...")
}
