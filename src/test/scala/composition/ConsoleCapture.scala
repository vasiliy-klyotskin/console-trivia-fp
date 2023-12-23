package org.kyotskin.trivia
package composition

import cats.effect.{FiberIO, IO}
import cats.effect.unsafe.implicits.global
import presentation.{ErrorViewModel, GreetingViewModel}
import ui.*

import java.io.{ByteArrayInputStream, ByteArrayOutputStream, PrintStream}

class ConsoleCapture() {
  private val outputStream: ByteArrayOutputStream = new ByteArrayOutputStream

  def runWithCapturingOutput(trivia: IO[Unit]): Unit = {
    scala.Console.withOut(new PrintStream(outputStream)) {
      trivia.unsafeRunSync()
    }
  }

  def input(value: String): Unit = {
    val inputStream = new ByteArrayInputStream(value.getBytes)
    System.setIn(inputStream)
  }

  // TODO: Do something with duplication
  def isDisplaying(vm: GreetingViewModel): Boolean = isDisplaying(vm.textItem())
  def isDisplaying(vm: ErrorViewModel): Boolean = isDisplaying(vm.textItem())
  
  private def isDisplaying(textItem: TextItem): Boolean = {
    stringOutput().contains(textItem)
  }

  private def stringOutput(): String = {
    outputStream.toString
  }
}