package org.kyotskin.trivia
package presentation

case class ErrorViewModel(text: String)

def categoryInputErrorViewModel: ErrorViewModel = {
  ErrorViewModel("Invalid input. Please enter valid category numbers separated by spaces, or type 'all' to play with questions from all categories.")
}

def difficultyChoiceErrorViewModel: ErrorViewModel = {
  ErrorViewModel("Invalid input. Please enter a valid number corresponding to your desired difficulty level.")
}

def answerInputErrorViewModel: ErrorViewModel = {
  ErrorViewModel("Invalid input. Please enter a valid number corresponding to chosen answer.")
}
