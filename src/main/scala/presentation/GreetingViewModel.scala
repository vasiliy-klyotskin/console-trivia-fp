package org.kyotskin.trivia
package presentation

case class GreetingViewModel(
  title: String,
  message: String,
  requestToProceed: String
)

def greetingViewModel: GreetingViewModel = {
  val title = "Welcome to the Console Trivia Game!"
  val message = "Get ready to test your knowledge and have a blast with our trivia questions. Whether you're a seasoned trivia pro or just looking for some fun, this game is perfect for everyone. Answer questions from a variety of categories, rack up points, and compete with friends or challenge yourself.\nLet's see how much you know and how well you can think on your feet. Are you ready to embark on this thrilling trivia adventure? Good luck and have a fantastic time!"
  val requestToProceed = "Press 'Enter' to continue."
  GreetingViewModel(title, message, requestToProceed)
}