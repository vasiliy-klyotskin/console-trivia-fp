package org.kyotskin.trivia
package presentation

import domain.*
import org.scalatest.funsuite.AnyFunSuite

class MapToQuestionViewModelTests extends AnyFunSuite {
  test("Sut maps minimal question model to view model") {
    val question = Question("", possibleAnswers = List.empty, correctAnswer = "any")
    val expected = QuestionViewModel(
      question = "",
      possibleAnswers = List.empty
    )
    assert(mapToQuestionViewModel(question) == expected)
  }

  test("Sut maps question model to view model") {
    val possibleAnswers: List[String] = List(
      "Baku",
      "Moscow",
      "Colombo",
      "Novosibirsk"
    )
    val question = Question("What is the capital of Russia?", possibleAnswers, correctAnswer = "any")
    val expected = QuestionViewModel(
      question = "What is the capital of Russia?",
      possibleAnswers = List(
        "1. Baku",
        "2. Moscow",
        "3. Colombo",
        "4. Novosibirsk",
      )
    )
    assert(mapToQuestionViewModel(question) == expected)
  }
}