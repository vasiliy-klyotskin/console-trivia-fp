package org.kyotskin.trivia
import domain.penaltyRate
import domain.Answer
import org.scalatest.funsuite.AnyFunSuite
import org.scalactic.Tolerance.convertNumericToPlusOrMinusWrapper

class PenaltyRateTests extends AnyFunSuite {
  test("Penalty rate for empty answers") {
    assertEqual(penaltyRate(List.empty), 1.0)
  }

  test("Penalty rate for one incorrect answer should be 0") {
    val answers = List(answer(isCorrect = false))
    assertEqual(penaltyRate(answers), 0.0)
  }

  test("Penalty for one correct answer should be 1") {
    val answers = List(answer(isCorrect = true))
    assertEqual(penaltyRate(answers), 1.0)
  }

  test("Penalty for many incorrect answers should be 0") {
    val answers = List(
      answer(isCorrect = false),
      answer(isCorrect = false),
      answer(isCorrect = false),
    )
    assertEqual(penaltyRate(answers), 0.0)
  }

  test("Penalty for many correct answers should be 1") {
    val answers = List(
      answer(isCorrect = true),
      answer(isCorrect = true),
      answer(isCorrect = true),
    )
    assertEqual(penaltyRate(answers), 1.0)
  }

  test("Penalty for many different answers should be equal to appropriate value") {
    val answers = List(
      answer(isCorrect = true),
      answer(isCorrect = false),
      answer(isCorrect = true),
      answer(isCorrect = false),
      answer(isCorrect = true),
    )
    // (8.5 / t^2) * (x - t)^2 = 3.06
    // x - count of mistakes
    // t - count of answers
    assertEqual(penaltyRate(answers), 0.306)
  }

  private def assertEqual(actual: Double, expected: Double): Unit = {
    assert(actual === expected +- 0.00000001)
  }

  private def answer(isCorrect: Boolean) = Answer(isCorrect, anyTime(), anyWordCount())

  private def anyTime(): Double = 0.0
  
  private def anyWordCount(): Int = 0
}