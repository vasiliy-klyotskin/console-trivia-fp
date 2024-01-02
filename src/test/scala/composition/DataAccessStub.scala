package org.kyotskin.trivia
package composition

import domain.Questions
import cats.effect.IO

class DataAccessStub() {
  private var stubbedQuestions: List[Option[Questions]] = List.empty
  private var stubbedTime: List[Double] = List.empty

  def fetchQuestions(url: String): IO[Option[Questions]] = {
    if (stubbedQuestions.isEmpty) throw Exception()
    val next = stubbedQuestions.headOption.flatten
    stubbedQuestions = stubbedQuestions.tail
    IO.pure(next)
  }

  def measureTime: IO[Double] = {
    if (stubbedTime.isEmpty) return IO.pure(0.0)
    val next = stubbedTime.head
    stubbedTime = stubbedTime.tail
    IO.pure(next)
  }

  def stubTime(time: Double): Unit = {
    stubbedTime = stubbedTime.appended(time)
  }

  def stubQuestions(questions: Option[Questions]): Unit = {
    stubbedQuestions = stubbedQuestions.appended(questions)
  }
}