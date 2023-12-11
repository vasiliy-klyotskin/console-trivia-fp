package org.kyotskin.trivia
package networking
import domain.Question

def map(remote: QuestionRemoteDTO): Question = {
  Question(text = remote.question.text, possibleAnswers = List(remote.correctAnswer), correctAnswer = remote.correctAnswer)
}
