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

  test("Score for one answer which has been solved instantaneously") {
    val answers = List(Answer(isCorrect = true, time = 0, wordsCountInQuestion = 16))
    assert(score(answers) == 24)
  }

  test("Score for one answer which has been solved after 1 sec") {
    val answers = List(Answer(isCorrect = true, time = 1, wordsCountInQuestion = 16))
    assert(score(answers) == 21)
  }

  test("Score for one answer which has been solved after maximum time that is considered fast") {
    val answers = List(Answer(isCorrect = true, time = 7, wordsCountInQuestion = 16))
    assert(score(answers) == 3)
  }

  private def anyTime(): Double = 0.0
  private def anyWordCount(): Int = 0
}