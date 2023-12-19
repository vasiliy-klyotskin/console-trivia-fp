package org.kyotskin.trivia
package presentation

import domain.*

def validateName(input: String): Option[PlayerName] = {
  Option(input.trim)
    .filter(_.nonEmpty)
    .filter(_.forall(char => char.isLetter || char.isWhitespace))
}

def validateCategory(availableCategories: List[Category])(input: String): Option[CategoryChoice] = {
  if (input.trim == "all") {
    Some(CategoryChoice.All)
  } else {
    specificCategories(input, availableCategories).map(CategoryChoice.Specific(_))
  }
}

private def specificCategories(input: String, allCategories: List[Category]): Option[List[Category]] = {
  Option(input.trim.split("\\s+").toList)
    .filter(areAllNumbers)
    .map(toIndexes)
    .filter(areIndexesCorrectFor(allCategories))
    .map(toSelectedCategoriesIn(allCategories))
}

private def toSelectedCategoriesIn(categories: List[Category])(indexes: List[Int]): List[Category] = {
  indexes.map(categories.apply)
}

private def areIndexesCorrectFor(categories: List[Category])(indexes: List[Int]): Boolean = {
  indexes.forall(index => index >= 0 && index < categories.length)
}

private def toIndexes(entries: List[String]): List[Int] = {
  entries.flatMap(_.toIntOption.map(_-1))
}
private def areAllNumbers(entries: List[String]): Boolean = {
  entries.forall(_.toIntOption.isDefined)
}