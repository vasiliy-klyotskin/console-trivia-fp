package org.kyotskin.trivia
import org.scalatest.funsuite.AnyFunSuite

class ScoreTests extends AnyFunSuite {
  test("Score equals zero on no answers") {
    assert(score(List.empty) == 0)
  }

  test("Score for one answer which hasn't been solved") {
    val answers = List(Answer(isCorrect = false, anyTime(), anyWordCount()))
    assert(score(answers) == 0)
  }

  test("Score for one answer which has been solved fast for 0 sec") {
    val answers = List(Answer(isCorrect = true, time = 0, wordsCountInQuestion = 16))
    assert(score(answers) == 24)
  }

  test("Score for one answer which has been solved fast for 1 sec") {
    val answers = List(Answer(isCorrect = true, time = 1, wordsCountInQuestion = 16))
    assert(score(answers) == 21)
  }

  test("Score for one answer which has been solved in maximum time that is considered fast") {
    val answers = List(Answer(isCorrect = true, time = 7, wordsCountInQuestion = 16))
    assert(score(answers) == 3)
  }

  test("Score for one answer which has been solved in the least time that is considered slow") {
    val answers = List(Answer(isCorrect = true, time = 10, wordsCountInQuestion = 16))
    assert(score(answers) == 1)
  }

  test("Score for one answer which has been solved slow") {
    val answers = List(Answer(isCorrect = true, time = 999, wordsCountInQuestion = 16))
    assert(score(answers) == 1)
  }

  test("Score for one answer which has been solved in normal time") {
    val answers = List(Answer(isCorrect = true, time = 8, wordsCountInQuestion = 16))
    assert(score(answers) == 2)
  }

  test("Score for two correct answers in a row of 2") {
    val answers = List(
      Answer(isCorrect = true, time = 6, wordsCountInQuestion = 16),
      Answer(isCorrect = true, time = 4, wordsCountInQuestion = 16)
    )
    assert(score(answers) == 21) // 6 + 15.6 = 21.6 => 21
  }

  test("Score for three correct answers in a row of 3") {
    val answers = List(
      Answer(isCorrect = true, time = 4, wordsCountInQuestion = 16),
      Answer(isCorrect = true, time = 6, wordsCountInQuestion = 16),
      Answer(isCorrect = true, time = 5, wordsCountInQuestion = 16)
    )
    assert(score(answers) == 34) // 12 + 7,8 + 14,4 = 34,2 => 34
  }

  private def anyTime(): Double = 0.0
  private def anyWordCount(): Int = 0
}