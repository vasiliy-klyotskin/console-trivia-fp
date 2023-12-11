package org.kyotskin.trivia
package networking

import org.scalatest.funsuite.AnyFunSuite
import domain.Question

class QuestionRemoteMappingTests extends AnyFunSuite {
  test("No incorrect answers in dto") {
    val textDto = QuestionTextRemoteDTO(text = "some question")
    val dto = QuestionRemoteDTO(
      id = "any",
      question = textDto,
      correctAnswer = "correct answer",
      incorrectAnswers = List.empty
    )
    val model = Question(
      text = "some question",
      possibleAnswers = List("correct answer"),
      correctAnswer = "correct answer"
    )
    assert(map(dto) == model)
  }
}