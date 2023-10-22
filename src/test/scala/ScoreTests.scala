package org.kyotskin.trivia
import org.kyotskin.trivia.Difficulty.*
import org.scalatest.funsuite.AnyFunSuite

class ScoreTests extends AnyFunSuite {
  test("Score equals zero on no answers") {
    assert(score(List.empty, Easy) == 0)
  }
}