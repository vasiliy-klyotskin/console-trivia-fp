package org.kyotskin.trivia
package domain

def fullScore(answers: List[Answer], difficulty: Difficulty): Int = {
  val baseScoreForAllAnswers = baseScore(answers)
  val penaltyForIncorrectAnswers = penaltyRate(answers)
  val difficultyRate = difficulty match {
    case Difficulty.Easy => 10
    case Difficulty.Medium => 20
    case Difficulty.Hard => 30
  }
  (baseScoreForAllAnswers * penaltyForIncorrectAnswers * difficultyRate).toInt
}
def baseScore(answers: List[Answer]): Int = {
  answers.zip(streaks(answers)).map(scoreOne).sum.toInt
}

def penaltyRate(answers: List[Answer]): Double = {
  if answers.isEmpty || answers.forall(_.isCorrect) then {
    1.0
  } else {
    val mistakesCount = answers.count(!_.isCorrect).toDouble
    val answersCount = answers.length.toDouble
    val correctCount = answersCount - mistakesCount
    (0.85 / (answersCount * answersCount)) * (correctCount * correctCount)
  }
}

private def streaks(answers: List[Answer]): List[Int] = {
  answers
    .inits
    .toList
    .reverse
    .map(_.reverse.takeWhile(isCorrectAndFast).length)
    .init
}

private def scoreOne(answer: Answer, streak: Int): Double = {
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

private def scoreForCorrectAndFast(answer: Answer, streak: Int): Double = {
  val remainingTimeUntilAnswerIsNotFast = maxTimeThatIsConsideredFast(answer) - answer.time
  val streakRate = 1 + streak * 0.3
  val score = 3 * remainingTimeUntilAnswerIsNotFast + 3
  score * streakRate
}

private def maxTimeThatIsConsideredFast(answer: Answer): Double = {
  answer.wordsCountInQuestion.toDouble * averageReadingTimeRate() + 3
}

private def isCorrectAndFast(answer: Answer): Boolean = {
  answer.isCorrect && isFast(answer)
}

private def isFast(answer: Answer): Boolean = {
  answer.time <= maxTimeThatIsConsideredFast(answer)
}

private def isSlow(answer: Answer): Boolean = {
  answer.time >= answer.wordsCountInQuestion.toDouble * averageReadingTimeRate() + 6
}

private def averageReadingTimeRate(): Double = 1.0 / 4.0