package org.kyotskin.trivia
package presentation

import domain.*

def validateCategory(availableCategories: List[Category])(input: String): Option[CategoryChoice] = {
  if (input.trim == "all") {
    Some(CategoryChoice.All)
  } else {
    specificCategories(input, availableCategories).map(CategoryChoice.Specific(_))
  }
}

def validateDifficulty(input: String): Option[Difficulty] = {
  Option(input.trim)
    .filter(_.nonEmpty)
    .flatMap(_.toIntOption)
    .filter(input => input > 0 && input < 4)
    .map(index => Difficulty.fromOrdinal(index - 1))
}

def validateAnswer(question: Question, time: Double)(input: String): Option[Answer] = {
  Option(input.trim)
    .flatMap(_.toIntOption)
    .flatMap(index => question.possibleAnswers.lift(index - 1))
    .map(_ == question.correctAnswer)
    .map(isAnswerCorrect => Answer(isAnswerCorrect, time, question.text.trim.split("\\s+").length))
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