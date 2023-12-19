package org.kyotskin.trivia
package networking

import org.scalatest.funsuite.AnyFunSuite
import domain.*

class QuestionRemoteMappingTests extends AnyFunSuite {
  test("Url for difficulty is correct when all categories are selected") {
    val difficulties = List(Difficulty.Easy, Difficulty.Medium, Difficulty.Hard)
    val expectedUrls = List(
      "https://the-trivia-api.com/v2/questions?limit=10&difficulty=easy",
      "https://the-trivia-api.com/v2/questions?limit=10&difficulty=medium",
      "https://the-trivia-api.com/v2/questions?limit=10&difficulty=hard"
    )

    assert(difficulties.map(questionsUrl(_, CategoryChoice.All)) == expectedUrls)
  }

  test("Url for difficulty is correct when some categories are selected") {
    val selectedCategories = List(Category.Music, Category.Science)
    val expectedUrl = "https://the-trivia-api.com/v2/questions?limit=10&difficulty=easy&categories=music,science"
    val result = questionsUrl(Difficulty.Easy, CategoryChoice.Specific(selectedCategories))

    assert(result == expectedUrl)
  }
}