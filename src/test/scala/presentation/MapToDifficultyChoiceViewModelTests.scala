package org.kyotskin.trivia
package presentation

import domain.*
import org.scalatest.funsuite.AnyFunSuite

class MapToDifficultyChoiceViewModelTests extends AnyFunSuite {
  test("Sut maps name and difficulty models to view model") {
    val difficulties = List(Difficulty.Easy, Difficulty.Medium, Difficulty.Hard)
    val result = mapToDifficultyChoiceViewModel(difficulties)
    assert(result.message.nonEmpty)
    assert(result.difficulties == List("1. Easy", "2. Medium", "3. Hard"))
  }
}