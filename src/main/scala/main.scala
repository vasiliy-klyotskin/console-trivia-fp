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
  answers.foldLeft(0)((ac, answer) =>
    if (isCorrectAndFast(answer)) {
      scoreForCorrectAndFast(answer)
    } else if (isCorrectAndSlow(answer)) {
      1
    } else if (answer.isCorrect) {
      2
    } else {
      0
    }
  )
}


def scoreForCorrectAndFast(answer: Answer) = {
  val remainingTimeUntilAnswerIsNotFast = maxTimeThatIsConsideredFast(answer) - answer.time
  (3 * remainingTimeUntilAnswerIsNotFast + 3).toInt
}

def isCorrectAndFast(answer: Answer): Boolean = {
  answer.isCorrect && isFast(answer)
}

def isCorrectAndSlow(answer: Answer): Boolean = {
  answer.isCorrect && isSlow(answer)
}

def isFast(answer: Answer): Boolean = {
  answer.time <= maxTimeThatIsConsideredFast(answer)
}

def isSlow(answer: Answer): Boolean = {
  answer.time >= leastTimeThatIsConsideredSlow(answer)
}

def maxTimeThatIsConsideredFast(answer: Answer): Double = {
  answer.wordsCountInQuestion.toDouble / averageReadingTimeRate() + 3
}

def leastTimeThatIsConsideredSlow(answer: Answer): Double = {
  answer.wordsCountInQuestion.toDouble / averageReadingTimeRate() + 6
}

def averageReadingTimeRate(): Double = 4