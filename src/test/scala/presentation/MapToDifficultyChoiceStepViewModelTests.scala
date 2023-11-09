package org.kyotskin.trivia
package presentation

import domain.*
import org.scalatest.funsuite.AnyFunSuite

class MapToDifficultyChoiceStepViewModelTests extends AnyFunSuite {
  test("Sut maps name and difficulty models to view model") {
    val difficulties = List(Difficulty.Easy, Difficulty.Medium, Difficulty.Hard)
    val result = mapToDifficultyChoiceStepViewModel("John Connor", difficulties)
    assert(result.message == "Fantastic, John Connor! You've selected your categories. Now, it's time to choose the difficulty level for your trivia adventure. Please enter the number corresponding to your desired difficulty level:")
    assert(result.difficulties == List("1. Easy", "2. Medium", "3. Hard"))
  }
}