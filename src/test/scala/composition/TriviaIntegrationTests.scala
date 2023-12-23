package org.kyotskin.trivia
package composition

import presentation.*
import ui.*

import cats.effect.IO
import org.scalatest.funsuite.AnyFunSuite
import cats.effect.unsafe.implicits.global

type View = ConsoleCapture

class TriviaIntegrationTests extends AnyFunSuite {
  test("Displays greetings on start") {
    val view = makeSut()

    assert(view.isDisplaying(greetingViewModel()))
  }

  // TODO: we need a smarter way of doing this
  test("Displays error on incorrect name input") {
    val view = makeSut()

    view.input("!@#$%^&*(")

    assert(view.isDisplaying(greetingViewModel()))
    assert(view.isDisplaying(nameInputErrorViewModel()))
  }

  private def makeSut(): View = {
    val view = ConsoleCapture()
    val trivia = composeTrivia(uiEffects)
    view.runWithCapturingOutput(trivia)
    view
  }

  private def uiEffects: UI = new UI {
    override def display(textItem: TextItem): IO[Unit] = ui.printItem(textItem)
    override def input: IO[String] = ui.readLine
  }
}