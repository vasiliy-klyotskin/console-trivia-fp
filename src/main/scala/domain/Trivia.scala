package org.kyotskin.trivia
package domain

case class Trivia(
  questions: Questions,
  answers: List[Answer],
  difficulty: Difficulty
)

def initialTrivia(questions: Questions, difficulty: Difficulty): Trivia = {
  Trivia(questions, List.empty, difficulty)
}