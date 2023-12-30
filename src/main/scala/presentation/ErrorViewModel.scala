package org.kyotskin.trivia
package presentation

case class ErrorViewModel(text: String)

def nameInputErrorViewModel(): ErrorViewModel = {
  ErrorViewModel("Invalid input for the name. Please use only letters and spaces. Retry by entering a valid name.")
}

def categoryInputErrorViewModel(): ErrorViewModel = {
  ErrorViewModel("Invalid input. Please enter valid category numbers separated by spaces, or type 'all' to play with questions from all categories.")
}

def difficultyChoiceErrorViewModel(): ErrorViewModel = {
  ErrorViewModel("Invalid input. Please enter a valid number corresponding to your desired difficulty level.")
}
