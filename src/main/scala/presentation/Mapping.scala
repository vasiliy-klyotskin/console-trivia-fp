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

def greetingStep(): GreetingStepViewModel = {
  val greeting = "Welcome to the Console Trivia Game!\n\nGet ready to test your knowledge and have a blast with our trivia questions. Whether you're a seasoned trivia pro or just looking for some fun, this game is perfect for everyone. Answer questions from a variety of categories, rack up points, and compete with friends or challenge yourself.\n\nLet's see how much you know and how well you can think on your feet. Are you ready to embark on this thrilling trivia adventure? Good luck and have a fantastic time!"
  val nameRequest = "Before we begin, please enter your name so we can personalize your experience and keep track of your score. Type your name and press Enter to get started."
  GreetingStepViewModel(greeting, nameRequest)
}