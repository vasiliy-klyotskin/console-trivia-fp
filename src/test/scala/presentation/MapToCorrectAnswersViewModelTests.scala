package org.kyotskin.trivia
package presentation

import domain.*
import org.scalatest.funsuite.AnyFunSuite

class MapToCorrectAnswersViewModelTests extends AnyFunSuite {
  test("sut maps empty questions to empty view model") {
    assert(mapToCorrectAnswersViewModel(List.empty) == CorrectAnswersViewModel(List.empty))
  }

  test("sut maps questions to view model") {
    val questions = List(
      Question("Question 0", anyPossibleAnswers(), correctAnswer = "Answer 0"),
      Question("Question 1", anyPossibleAnswers(), correctAnswer = "Answer 1"),
      Question("Question 2", anyPossibleAnswers(), correctAnswer = "Answer 2")
    )
    val expected = CorrectAnswersViewModel(List(
      CorrectAnswersViewModelItem("1. Question 0", "Correct Answer: Answer 0"),
      CorrectAnswersViewModelItem("2. Question 1", "Correct Answer: Answer 1"),
      CorrectAnswersViewModelItem("3. Question 2", "Correct Answer: Answer 2")
    ))
    assert(mapToCorrectAnswersViewModel(questions) == expected)
  }

  private def anyPossibleAnswers(): List[String] = List.empty
}