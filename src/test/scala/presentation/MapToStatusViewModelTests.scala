package org.kyotskin.trivia
package presentation

import domain.*
import org.scalatest.funsuite.AnyFunSuite

class MapToStatusViewModelTests extends AnyFunSuite {
  test("Sut maps minimal trivia model to view model") {
    val (trivia, score) = makeTriviaAndScore(0, List.empty)
    val expected = StatusViewModel(
      progressTitle = "Progress:   0 / 0",
      scoreTitle = s"Score:      $score",
      progressIndicator = ""
    )
    assert(mapToStatusViewModel(trivia) == expected)
  }

  private def makeTriviaAndScore(questionsCount: Int, answersIsCorrectCriteria: List[Boolean]): (Trivia, Int) = {
    val questions = (0 until questionsCount).map(_ => Question("any", List.empty, "any")).toList
    val anyTime = 2
    val anyWordsCount = 4
    val answers = answersIsCorrectCriteria.map(isCorrect => Answer(isCorrect, anyTime, anyWordsCount))
    val difficulty = Difficulty.Medium
    (Trivia(questions, answers, difficulty), fullScore(answers, difficulty))
  }
}