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

  private def anyTime(): Double = 0.0
  private def anyWordCount(): Int = 0
}