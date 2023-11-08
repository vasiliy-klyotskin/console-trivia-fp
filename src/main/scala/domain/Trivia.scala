package org.kyotskin.trivia
package domain

case class Trivia(
  questions: List[Question],
  answers: List[Answer],
  difficulty: Difficulty
)