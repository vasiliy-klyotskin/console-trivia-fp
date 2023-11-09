package org.kyotskin.trivia
package presentation

import domain.*
import org.scalatest.funsuite.AnyFunSuite

class MapToCategoryChoiceStepViewModelTests extends AnyFunSuite {
  test("Sut maps minimal categories model to view model") {
    val result = mapToCategoryChoiceViewModel(categories = List.empty)
    assert(result.message.nonEmpty)
    assert(result.categories.isEmpty)
  }

  test("Sut maps categories model to view model") {
    val categories = List(
      Category("Music"),
      Category("Science"),
      Category("Art")
    )
    val result = mapToCategoryChoiceViewModel(categories)
    val expectedMappedCategories = List(
      "1. Music",
      "2. Science",
      "3. Art"
    )
    assert(result.message.nonEmpty)
    assert(result.categories == expectedMappedCategories)
  }
}