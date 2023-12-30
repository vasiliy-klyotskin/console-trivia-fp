package org.kyotskin.trivia
package composition

import cats.effect.IO
import domain.*
import presentation.*
import ui.*

trait UI {
  def display(textItem: TextItem): IO[Unit]
  def clear: IO[Unit]
  def input: IO[String]
}

def composeTrivia(ui: UI): IO[Unit] = {
  for {
    playerName <- greetingsStep(ui)
    _ <- categoryStep(ui)
  } yield ()
}

def categoryStep(ui: UI): IO[Unit] = {
  for {
    _ <- ui.display(allCategoriesViewModel().textItem())
  } yield ()
}

def greetingsStep(ui: UI): IO[PlayerName] = {
  for {
    _ <- ui.display(greetingViewModel().textItem())
    result <- requestPlayerName(ui)
    _ <- ui.clear
  } yield result
}

def requestPlayerName(ui: UI): IO[PlayerName] = {
  val tryRequestAgain = ui.display(nameInputErrorViewModel().textItem()) >> requestPlayerName(ui)
  for {
    playerName <- ui.input.map(validateName)
    result <- playerName.fold(tryRequestAgain)(IO.pure)
  } yield result
}