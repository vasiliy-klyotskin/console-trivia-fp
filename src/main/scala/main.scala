package org.kyotskin.trivia

import cats.effect.unsafe.implicits.global
import composition.*
import ui.*

import cats.effect.IO
@main
def main(): Unit = {
  composeTrivia(uiEffects).unsafeRunSync()
}

private def uiEffects: UI = new UI {
  override def display(textItem: TextItem): IO[Unit] = ui.printItem(textItem)
  override def input: IO[String] = ui.readLine
}