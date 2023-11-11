package org.kyotskin.trivia
package presentation

import domain.*
import org.scalatest.funsuite.AnyFunSuite

class MapToTriviaResultsViewModelTests extends AnyFunSuite {
  test("maps minimal trivia model to a view model") {
    val minimalTrivia = Trivia(questions = List.empty, answers = List.empty, difficulty = Difficulty.Medium)
    val player = "Player Name"
    val result = mapToTriviaResultsViewModel(minimalTrivia, player)
    val expected = TriviaResultsViewModel(title = "", feedback = "", metrics = List.empty, farewellMessage = "")

    assert(result == expected)
  }

  test("maps trivia model to a view model with correct title, farewell message and metrics") {
    val questions = List(anyQuestion(), anyQuestion(), anyQuestion())
    val answers = List(Answer(true, 1, 4), Answer(false, 2, 4) , Answer(true, 3, 4))
    val trivia = Trivia(questions, answers, difficulty = Difficulty.Medium)
    val player = "John Connor"
    val result = mapToTriviaResultsViewModel(trivia, player)

    assert(result.title.contains(player))
    assert(result.farewellMessage.nonEmpty)
    assert(result.metrics == List(
      s"Your Score: ${fullScore(answers, Difficulty.Medium)}",
      "Accuracy: 2 / 3"
    ))
  }

  test("maps trivia low score model to a view model with correct feedback") {
    val questionCount = 10
    val cases = List(0, 1, 2, 3, 4)
    cases.foreach { correctAnswersCount =>
      val trivia = triviaFor(questionCount, correctAnswersCount)
      val player = "John Connor"
      val result = mapToTriviaResultsViewModel(trivia, player)

      assert(result.feedback == "Looks like there's room for improvement, John Connor. Don't worry, practice makes perfect!")
    }
  }

  test("maps trivia average score model to a view model with correct feedback") {
    val questionCount = 10
    val cases = List(5, 6, 7)
    cases.foreach { correctAnswersCount =>
      val trivia = triviaFor(questionCount , correctAnswersCount)
      val player = "John Connor"
      val result = mapToTriviaResultsViewModel(trivia, player)

      assert(result.feedback == "Not bad, John Connor! You're on the right track. Keep challenging yourself to improve.")
    }
  }

  test("maps trivia good score model to a view model with correct feedback") {
    val questionCount = 10
    val cases = List(8, 9)
    cases.foreach { correctAnswersCount =>
      val trivia = triviaFor(questionCount, correctAnswersCount)
      val player = "John Connor"
      val result = mapToTriviaResultsViewModel(trivia, player)

      assert(result.feedback == "Well done, John Connor! You have a solid knowledge base. Keep up the great work!")
    }
  }

  test("maps trivia highest score model to a view model with correct feedback") {
    val trivia = triviaFor(questionCount = 10, correctAnswersCount = 10)
    val player = "John Connor"
    val result = mapToTriviaResultsViewModel(trivia, player)

    assert(result.feedback == "Outstanding, John Connor! You're a trivia genius. Congratulations on achieving a remarkable score!")
  }

  private def anyQuestion(): Question = Question("any text", List.empty, "any")

  private def triviaFor(questionCount: Int, correctAnswersCount: Int): Trivia = {
    val questions = List.fill(questionCount)(anyQuestion())
    val correctAnswers = List.fill(correctAnswersCount)(Answer(true, 1, 1))
    val incorrectAnswers = List.fill(questionCount - correctAnswersCount)(Answer(false, 1, 1))
    Trivia(questions, correctAnswers.appendedAll(incorrectAnswers), difficulty = Difficulty.Medium)
  }
}