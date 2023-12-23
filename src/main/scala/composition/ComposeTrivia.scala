package org.kyotskin.trivia
package composition

import cats.effect.IO
import domain.*
import presentation.*
import ui.*

trait UI {
  def display(textItem: TextItem): IO[Unit]
  def input: IO[String]
}

def composeTrivia(ui: UI): IO[Unit] = {
  greetingsStep(ui).map(player => ())
}

def greetingsStep(ui: UI): IO[PlayerName] = {
  for {
    _ <- ui.display(greetingViewModel().textItem())
    validatedName <- ui.input.map(validateName)
    result <- validatedName.fold(ui.display(nameInputErrorViewModel().textItem()) >> greetingsStep(ui))(IO.pure)
  } yield result
}