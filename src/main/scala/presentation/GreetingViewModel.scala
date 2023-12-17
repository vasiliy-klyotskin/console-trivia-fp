package org.kyotskin.trivia
package presentation

case class GreetingViewModel(
  greeting: String,
  nameRequest: String
)

def greetingViewModel(): GreetingViewModel = {
  val greeting = "Welcome to the Console Trivia Game!\n\nGet ready to test your knowledge and have a blast with our trivia questions. Whether you're a seasoned trivia pro or just looking for some fun, this game is perfect for everyone. Answer questions from a variety of categories, rack up points, and compete with friends or challenge yourself.\n\nLet's see how much you know and how well you can think on your feet. Are you ready to embark on this thrilling trivia adventure? Good luck and have a fantastic time!"
  val nameRequest = "Before we begin, please enter your name so we can personalize your experience and keep track of your score. Type your name and press Enter to get started."
  GreetingViewModel(greeting, nameRequest)
}