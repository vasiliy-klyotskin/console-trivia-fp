package org.kyotskin.trivia
package composition

import presentation.*
import ui.*
import cats.effect.IO
import org.scalatest.funsuite.AnyFunSuite
import cats.effect.unsafe.implicits.global

type Spy = TextUISpy

class TriviaIntegrationTests extends AnyFunSuite {
  test("Displays greetings on start") {
    val (spy, program) = makeSut()

    program.unsafeRunSync()

    assert(spy.displayedTimes(greetingViewModel().textItem()) == 1)
  }

  test("Displays error on incorrect name input") {
    val (spy, program) = makeSut()
    spy.stub("!@#$%^&*")
    spy.stub("123#!(*")

    program.unsafeRunSync()

    assert(spy.displayedTimes(greetingViewModel().textItem()) == 1)
    assert(spy.displayedTimes(nameInputErrorViewModel().textItem()) == 2)
  }

  test("Displays categories choice on correct name input") {
    val (spy, program) = makeSut()
    spy.stub("Vasiliy")

    program.unsafeRunSync()

    assert(spy.displayedTimes(greetingViewModel().textItem()) == 0)
    assert(spy.displayedTimes(allCategoriesViewModel().textItem()) == 1)
  }

  test("Displays categories choice error on incorrect categories input") {
    val (spy, program) = makeSut()
    spy.stub("Vasiliy")
    spy.stub("#@(#*$")
    spy.stub("(*&^%$")

    program.unsafeRunSync()

    assert(spy.displayedTimes(allCategoriesViewModel().textItem()) == 1)
    assert(spy.displayedTimes(categoryInputErrorViewModel().textItem()) == 2)
  }

  test("Displays difficulty choice on correct categories choice") {
    val (spy, program) = makeSut()
    spy.stub("Vasiliy")
    spy.stub("1 3")

    program.unsafeRunSync()

    assert(spy.displayedTimes(allCategoriesViewModel().textItem()) == 0)
    assert(spy.displayedTimes(allDifficultiesViewModel("Vasiliy").textItem()) == 1)
  }

  test("Displays difficulty choice error on incorrect difficulty choice") {
    val (spy, program) = makeSut()
    spy.stub("Vasiliy")
    spy.stub("1 3")
    spy.stub("!@#$%^&*")
    spy.stub("(*&^%$#)")

    program.unsafeRunSync()

    assert(spy.displayedTimes(allDifficultiesViewModel("Vasiliy").textItem()) == 1)
    assert(spy.displayedTimes(difficultyChoiceErrorViewModel().textItem()) == 2)
  }

  private def makeSut(): (Spy, IO[Unit]) = {
    val spy = TextUISpy()
    val trivia = composeTrivia(ui(spy)).handleError(_ => ())
    (spy, trivia)
  }

  private def ui(spy: Spy): UI = new UI {
    override def display(textItem: TextItem): IO[Unit] = spy.display(textItem)
    override def clear: IO[Unit] = spy.clear()
    override def input: IO[String] = spy.input()
  }
}