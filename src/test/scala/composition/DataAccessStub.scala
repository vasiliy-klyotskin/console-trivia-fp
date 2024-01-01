package org.kyotskin.trivia
package composition

import domain.Questions
import cats.effect.IO

class DataAccessStub() {
  private var stubbedQuestions: List[Option[Questions]] = List.empty

  def fetchQuestions(url: String): IO[Option[Questions]] = {
    IO.delay {
      if (stubbedQuestions.isEmpty) throw Exception()
      val next = stubbedQuestions.headOption.flatten
      stubbedQuestions = stubbedQuestions.tail
      next
    }
  }

  def stubQuestions(questions: Option[Questions]): Unit = {
    stubbedQuestions = stubbedQuestions.appended(questions)
  }
}
