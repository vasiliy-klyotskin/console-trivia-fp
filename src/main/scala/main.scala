package org.kyotskin.trivia

import cats.effect.unsafe.implicits.global
import cats.effect.IO
import composition.*
import ui.*
import networking.*
import domain.*

val httpClient = createHttpClient()
val loadRemoteQuestions = fetchRemoteQuestions(httpClient)
val timeMeasure = IO.monotonic.map(_.toMillis.toDouble / 1000)

private def uiFunctions: UI = new UI {
  override def display(textItem: TextItem): IO[Unit] = printItem(textItem)
  override def clear: IO[Unit] = printItem(clearItem)
  override def input: IO[String] = readLine
}

private def dataAccess: DataAccess = new DataAccess {
  override def fetchQuestions(url: String): IO[Option[Questions]] = loadRemoteQuestions(url).map(toQuestionsModel)
  override def measureTime: IO[Double] = timeMeasure
}

@main
def main(): Unit = {
  composeTrivia(uiFunctions, dataAccess).unsafeRunSync()
}