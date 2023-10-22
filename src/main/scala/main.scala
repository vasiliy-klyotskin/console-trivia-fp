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

case class Answer(isCorrect: Boolean, time: Double, wordsCountInQuestion: Int)

def score(answers: List[Answer]): Int = 0