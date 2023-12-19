package org.kyotskin.trivia
package composition

import presentation.*
import ui.*

import org.scalatest.funsuite.AnyFunSuite

type StartTrivia = () => Unit
type View = ConsoleCapture

class TriviaIntegrationTests extends AnyFunSuite {
  test("Displays greetings on start") {
    val (start, view) = makeSut()
    start()

    assert(view.isDisplaying(greetingViewModel()))
  }

  private def makeSut(): (StartTrivia, View) = {
    val view = ConsoleCapture()
    val trivia = composeTrivia(uiEffects)
    (view.runWithCapturingOutput(trivia), view)
  }

  private def uiEffects: UI = {
    (consoleItem: TextItem) => ui.display(consoleItem)
  }
}
