package org.kyotskin.trivia
package networking

import org.scalatest.funsuite.AnyFunSuite
import domain.{Difficulty, Question}

import cats.effect.IO
import cats.effect.unsafe.implicits.global

class QuestionRemoteMappingTests extends AnyFunSuite {
  test("Url for difficulty is correct") {
    val difficulties = List(Difficulty.Easy, Difficulty.Medium, Difficulty.Hard)
    val expectedUrls = List(
      "https://the-trivia-api.com/v2/questions?limit=10&difficulty=easy",
      "https://the-trivia-api.com/v2/questions?limit=10&difficulty=medium",
      "https://the-trivia-api.com/v2/questions?limit=10&difficulty=hard"
    )

    assert(difficulties.map(questionsUrl) == expectedUrls)
  }
}