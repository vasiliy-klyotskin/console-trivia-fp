package org.kyotskin.trivia
package presentation

import domain.*

private def indexedList(strings: List[String]): List[String] = {
  strings.zipWithIndex.map((str, index) => s"${index+1}. $str")
}