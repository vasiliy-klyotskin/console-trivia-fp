package org.kyotskin.trivia
package ui

import cats.effect.IO

def printItem(textItem: TextItem): IO[Unit] = {
  IO.delay {
    print(textItem)
  }
}

def readLine: IO[String] = {
  IO.blocking {
    scala.io.StdIn.readLine()
  }
}