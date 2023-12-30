package org.kyotskin.trivia
package presentation

import org.scalatest.funsuite.AnyFunSuite
import domain.*

class DifficultyValidationTests extends AnyFunSuite {
  test("wrong cases") {
    val results = validate(List("", " ", "   ", "0", "4", "1 2 3 4", "1a", "a1", "12", "123", "1all", "all1"))
    results.map(res => assert(res.isEmpty))
  }

  test("correct medium cases") {
    val results = validate(List("2", " 2 ", "2   ", " 2"))
    results.map(res => assert(res.contains(Difficulty.Medium)))
  }

  test("correct hard cases") {
    val results = validate(List("3", " 3 ", "3   ", " 3"))
    results.map(res => assert(res.contains(Difficulty.Hard)))
  }

  test("correct easy cases") {
    val results = validate(List("1", " 1 ", "1   ", " 1"))
    results.map(res => assert(res.contains(Difficulty.Easy)))
  }

  private def validate(inputs: List[String]): List[Option[Difficulty]] = {
    inputs.map(validateDifficulty)
  }
}