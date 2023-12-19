package org.kyotskin.trivia
package presentation

import org.scalatest.funsuite.AnyFunSuite
import domain.*

class CategoryValidationTests extends AnyFunSuite {
  test("wrong cases") {
    val results = validate(List("", " ", "   ", "0", "1 2 3 4", "1a", "a1", "12", "123", "1all", "all1"))
    results.map(res => assert(res.isEmpty))
  }

  test("user wants to choose all categories") {
    val expected = CategoryChoice.All
    val results = validate(List("all", " all", "  all   "))
    results.map(res => assert(res.contains(expected)))
  }

  test("user wants to choose music") {
    val expected = CategoryChoice.Specific(List(Category.Music))
    val results = validate(List("2", " 2 ", "     2", "2    "))
    results.map(res => assert(res.contains(expected)))
  }

  test("user wants to choose science and history") {
    val expected = CategoryChoice.Specific(List(Category.Science, Category.History))
    val results = validate(List("1 3", " 1 3 ", "1  3", " 1    3   "))
    results.map(res => assert(res.contains(expected)))
  }

  private def validate(inputs: List[String]): List[Option[CategoryChoice]] = {
    inputs.map(validateCategory(categories())(_))
  }

  private def categories(): List[Category] = {
    List(Category.Science, Category.Music, Category.History)
  }
}