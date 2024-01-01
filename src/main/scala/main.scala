package org.kyotskin.trivia

import cats.effect.unsafe.implicits.global
import composition.*
import ui.*

import cats.effect.IO
@main
def main(): Unit = {

}

private def makeUi: UI = new UI {
  override def display(textItem: TextItem): IO[Unit] = printItem(textItem)

  override def clear: IO[Unit] = printItem(clearItem)

  override def input: IO[String] = readLine
}