package org.kyotskin.trivia
package presentation

case class ErrorViewModel(text: String)

def nameInputErrorViewModel(): ErrorViewModel = {
  ErrorViewModel("Invalid input for the name. Please use only letters and spaces. Retry by entering a valid name.")
}