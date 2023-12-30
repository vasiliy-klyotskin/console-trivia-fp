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
    _ <- difficultyStep(ui, playerName)
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

def categoryStep(ui: UI): IO[Unit] = {
  for {
    _ <- ui.display(allCategoriesViewModel().textItem())
    _ <- requestCategories(ui)
    _ <- ui.clear
  } yield ()
}

def requestCategories(ui: UI): IO[Unit] = {
  val tryRequestAgain = ui.display(categoryInputErrorViewModel().textItem()) >> requestCategories(ui)
  for {
    categoryChoice <- ui.input.map(validateCategory(allCategories()))
    result <- categoryChoice.fold(tryRequestAgain)(IO.pure)
  } yield ()
}

def difficultyStep(ui: UI, playerName: PlayerName): IO[Unit] = {
  for {
    _ <- ui.display(allDifficultiesViewModel(playerName).textItem())
    _ <- requestDifficultyInput(ui)
  } yield ()
}

def requestDifficultyInput(ui: UI): IO[Unit] = {
  val tryRequestAgain = ui.display(difficultyChoiceErrorViewModel().textItem()) >> requestDifficultyInput(ui)
  for {
    difficulty <- ui.input.map(validateDifficulty)
    _ <- difficulty.fold(tryRequestAgain)(IO.pure)
  } yield ()
}