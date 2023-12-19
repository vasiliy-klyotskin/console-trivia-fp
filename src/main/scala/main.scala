package org.kyotskin.trivia

import cats.effect.unsafe.implicits.global
import composition.*
import ui.*
@main
def main(): Unit = {
  composeTrivia(uiEffects).unsafeRunSync()
}

private def uiEffects: UI = {
  (consoleItem: TextItem) => ui.display(consoleItem)
}