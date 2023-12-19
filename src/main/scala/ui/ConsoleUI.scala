package org.kyotskin.trivia
package ui

import cats.effect.IO

def display(textItem: TextItem): IO[Unit] = {
  IO.delay {
    print(textItem)
  }
}