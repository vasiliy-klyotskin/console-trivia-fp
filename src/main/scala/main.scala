package org.kyotskin.trivia

@main
def main(): Unit = {
  println("Hello world!")
}

enum Difficulty {
  case Easy
  case Medium
  case Hard
}

case class Answer(isCorrect: Boolean, time: Double)

def score(answers: List[Answer], difficulty: Difficulty): Int = 0