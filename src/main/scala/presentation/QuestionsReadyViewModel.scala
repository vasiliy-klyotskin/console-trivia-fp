package org.kyotskin.trivia
package presentation

case class QuestionsReadyViewModel(text: String)

def questionsReadyViewModel: QuestionsReadyViewModel = {
  QuestionsReadyViewModel("Questions has been prepared! Press 'Enter' to start the trivia!")
}
