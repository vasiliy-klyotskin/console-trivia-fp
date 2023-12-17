package org.kyotskin.trivia
package presentation

import domain.PlayerName

def validateName(name: String): Option[PlayerName] = {
  Option(name.trim)
    .filter(_.nonEmpty)
    .filter(_.forall(s => s.isLetter || s.isWhitespace))
}


