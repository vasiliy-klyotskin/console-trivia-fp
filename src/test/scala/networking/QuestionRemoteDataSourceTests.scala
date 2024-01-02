package org.kyotskin.trivia
package networking

import org.scalatest.funsuite.AnyFunSuite
import cats.effect.unsafe.implicits.global
import domain.*
import networking.*

class QuestionRemoteDataSourceTests extends AnyFunSuite {
  test("data from remote service has been successfully received") {
    val difficulty = Difficulty.Hard
    val categories = List(Category.Science, Category.Music)
    val url = questionsUrl(difficulty, CategoryChoice.Specific(categories))
    val client = createHttpClient()

    val result = fetchRemoteQuestions(client)(url).unsafeRunSync().get

    assert(result.length == 20)
    assert(result.forall(_.difficulty == "hard"))
    assert(result.count(_.category == "music") + result.count(_.category == "science") == result.length)
  }
}