package org.kyotskin.trivia
package presentation

import org.scalatest.funsuite.AnyFunSuite
import domain.*

class AnswerValidationTests extends AnyFunSuite {
  test("empty answer is not allowed") {
    assert(validateAnswer(anyQuestion, fixedTime)("").isEmpty)
  }

  test("only spaces are not allowed in answer") {
    assert(validateAnswer(anyQuestion, fixedTime)("   ").isEmpty)
  }

  test("0 is not correct input") {
    assert(validateAnswer(anyQuestion, fixedTime)("0").isEmpty)
  }

  test("symbols other than numbers and spaces are not allowed in the answer") {
    val otherSymbols = "!@#$%^&*()_-+\n±§asf"

    otherSymbols.foreach(sym => assert(validateAnswer(anyQuestion, fixedTime)(sym.toString).isEmpty))
  }

  test("leading and trailing spaces are removed during validation") {
    val question = Question("any question text", List("answer one", "answer two", "answer three"), "answer two")
    val input = "  1  "
    val expected = Answer(false, 1.0, 3)

    assert(validateAnswer(question, 1.0)(input).contains(expected))
  }

  test("regular answer validates correctly") {
    val question = Question("any text", List("answer 1", "answer 2", "answer 3"), "answer 2")
    val input = "2"
    val expected = Answer(true, 2.0, 2)

    assert(validateAnswer(question, 2.0)(input).contains(expected))
  }

  private def anyQuestion: Question = {
    Question("any text", List("answer 1", "answer 2", "answer 3"), "answer 2")
  }

  private def fixedTime: Double = {
    2.0
  }
}