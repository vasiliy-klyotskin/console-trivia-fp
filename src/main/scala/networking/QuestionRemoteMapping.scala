package org.kyotskin.trivia
package networking
import domain.Question
import domain.Difficulty

def map(remote: QuestionRemoteDTO): Question = {
  Question(
    text = remote.question.text,
    possibleAnswers = remote.incorrectAnswers.appended(remote.correctAnswer),
    correctAnswer = remote.correctAnswer
  )
}

def questionsUrl(difficulty: Difficulty): String = {
  val difficultyString = difficulty.toString.toLowerCase
  s"https://the-trivia-api.com/v2/questions?limit=10&difficulty=$difficultyString"
}