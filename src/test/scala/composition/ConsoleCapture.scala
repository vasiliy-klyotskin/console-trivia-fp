package org.kyotskin.trivia
package composition

import cats.effect.IO
import cats.effect.unsafe.implicits.global
import presentation.GreetingViewModel
import ui.*

import java.io.{ByteArrayOutputStream, PrintStream}

class ConsoleCapture() {
  private val outputStream: ByteArrayOutputStream = new ByteArrayOutputStream

  def runWithCapturingOutput(trivia: IO[Unit])(): Unit = {
    scala.Console.withOut(new PrintStream(outputStream)) {
      trivia.unsafeRunSync()
    }
  }

  def isDisplaying(viewModel: GreetingViewModel): Boolean = {
    stringOutput().contains(viewModel.textItem())
  }

  private def stringOutput(): String = {
    outputStream.toString
  }
}