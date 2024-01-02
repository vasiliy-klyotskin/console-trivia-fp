package org.kyotskin.trivia
package composition

import cats.effect.IO
import ui.*
import domain.*

trait UI {
  def display(textItem: TextItem): IO[Unit]
  def clear: IO[Unit]
  def input: IO[String]
}

trait DataAccess {
  def fetchQuestions(url: String): IO[Option[Questions]]
  def measureTime: IO[Double]
}