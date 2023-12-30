package org.kyotskin.trivia
package composition

import cats.effect.IO
import ui.*

class TextUISpy() {
  private var displayedItems: List[TextItem] = List.empty
  private var stubbedInputs: List[String] = List.empty

  def display(item: TextItem): IO[Unit] = {
    IO.delay {
      displayedItems = displayedItems.appended(item)
    }
  }

  def stub(input: String): Unit = {
    stubbedInputs = stubbedInputs.appended(input)
  }

  def input(): IO[String] = {
    IO.delay {
      if (stubbedInputs.isEmpty) throw Exception()
      val next = stubbedInputs.headOption.getOrElse("")
      stubbedInputs = stubbedInputs.tail
      next
    }
  }
  
  def clear(): IO[Unit] = {
    IO.delay {
      displayedItems = List.empty
    }
  }
  
  def displayedTimes(textItem: TextItem): Int = {
    displayedItems.count(textItem == _)
  }
}