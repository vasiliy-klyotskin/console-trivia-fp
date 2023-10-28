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

def score(answers: List[Answer]): Int = {
  answers.zip(streaks(answers)).map(scoreOne).sum.toInt
}

def streaks(answers: List[Answer]): List[Int] = {
  answers
    .inits
    .toList
    .reverse
    .map(_.reverse.takeWhile(isCorrectAndFast).length)
    .init
}

def scoreOne(answer: Answer, streak: Int): Double = {
  if (isCorrectAndFast(answer)) {
    scoreForCorrectAndFast(answer, streak)
  } else if (answer.isCorrect && isSlow(answer)) {
    1
  } else if (answer.isCorrect) {
    2
  } else {
    0
  }
}

def scoreForCorrectAndFast(answer: Answer, streak: Int): Double = {
  val remainingTimeUntilAnswerIsNotFast = maxTimeThatIsConsideredFast(answer) - answer.time
  val streakRate = 1 + streak * 0.3
  val score = 3 * remainingTimeUntilAnswerIsNotFast + 3
  score * streakRate
}

def maxTimeThatIsConsideredFast(answer: Answer): Double = {
  answer.wordsCountInQuestion.toDouble / averageReadingTimeRate() + 3
}

def isCorrectAndFast(answer: Answer): Boolean = {
  answer.isCorrect && isFast(answer)
}

def isFast(answer: Answer): Boolean = {
  answer.time <= maxTimeThatIsConsideredFast(answer)
}

def isSlow(answer: Answer): Boolean = {
  answer.time >= answer.wordsCountInQuestion.toDouble / averageReadingTimeRate() + 6
}

def averageReadingTimeRate(): Double = 4