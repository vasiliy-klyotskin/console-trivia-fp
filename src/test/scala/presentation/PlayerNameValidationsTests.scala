package org.kyotskin.trivia
package presentation

import presentation.validateName

import org.scalatest.funsuite.AnyFunSuite

class PlayerNameValidationsTests extends AnyFunSuite {
  test("empty name is not allowed") {
    assert(validateName("").isEmpty)
  }

  test("symbols other than letters and spaces are not allowed in the name") {
    val otherSymbols = "1!@#$%^&*()_-+\n±§"
    otherSymbols.foreach(sym => assert(validateName(sym.toString).isEmpty))
  }

  test("leading and trailing spaces are removed during validation") {
    assert(validateName("  John Connor  ").contains("John Connor"))
  }

  test("regular name validates correctly") {
    assert(validateName("Vasiliy").contains("Vasiliy"))
  }

  test("only spaces are not allowed in name") {
    assert(validateName("   ").isEmpty)
  }
}