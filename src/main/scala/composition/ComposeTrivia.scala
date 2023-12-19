package org.kyotskin.trivia
package composition

import cats.effect.IO
import presentation.*
import ui.*

trait UI {
  def display(consoleItem: TextItem): IO[Unit]
}

def composeTrivia(ui: UI): IO[Unit] = {
  ui.display(greetingViewModel().textItem())
}