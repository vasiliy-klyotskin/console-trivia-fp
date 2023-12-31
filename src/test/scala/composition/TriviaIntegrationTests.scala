package org.kyotskin.trivia
package composition

import presentation.*
import ui.*
import domain.*
import cats.effect.IO
import org.scalatest.funsuite.AnyFunSuite
import cats.effect.unsafe.implicits.global

type ViewSpy = TextUISpy

class TriviaIntegrationTests extends AnyFunSuite {
  test("Displays greetings on start") {
    val (view, _, program) = makeSut()

    program.unsafeRunSync()

    assert(view.displayedTimes(greetingViewModel.textItem()) == 1)
  }

  test("Displays categories choice on any input") {
    val (view, _, program) = makeSut()
    view.stub("proceed")

    program.unsafeRunSync()

    assert(view.displayedTimes(greetingViewModel.textItem()) == 0)
    assert(view.displayedTimes(allCategoriesViewModel.textItem()) == 1)
  }

  test("Displays categories choice error on incorrect categories input") {
    val (view, _, program) = makeSut()
    view.stub("proceed")
    view.stub("#@(#*$")
    view.stub("(*&^%$")

    program.unsafeRunSync()

    assert(view.displayedTimes(allCategoriesViewModel.textItem()) == 1)
    assert(view.displayedTimes(categoryInputErrorViewModel.textItem()) == 2)
  }

  test("Displays difficulty choice on correct categories choice") {
    val (view, _, program) = makeSut()
    view.stub("proceed")
    view.stub("1 3")

    program.unsafeRunSync()

    assert(view.displayedTimes(allCategoriesViewModel.textItem()) == 0)
    assert(view.displayedTimes(allDifficultiesViewModel.textItem()) == 1)
  }

  test("Displays difficulty choice error on incorrect difficulty choice") {
    val (view, _, program) = makeSut()
    view.stub("proceed")
    view.stub("1 3")
    view.stub("!@#$%^&*")
    view.stub("(*&^%$#)")

    program.unsafeRunSync()

    assert(view.displayedTimes(allDifficultiesViewModel.textItem()) == 1)
    assert(view.displayedTimes(difficultyChoiceErrorViewModel.textItem()) == 2)
  }

  test("Displays questions loading message on correct difficulty selection") {
    val (view, dataAccess, program) = makeSut()
    view.stub("proceed")
    view.stub("1 3")
    view.stub("2")

    program.unsafeRunSync()

    assert(view.displayedTimes(allDifficultiesViewModel.textItem()) == 0)
    assert(view.displayedTimes(questionLoadingViewModel.textItem()) == 1)
  }

  test("Displays questions loading message once after correct difficulty selection and one fetching retry") {
    val (view, dataAccess, program) = makeSut()
    view.stub("proceed")
    view.stub("1 3")
    view.stub("2")
    dataAccess.stubQuestions(None)

    program.unsafeRunSync()

    assert(view.displayedTimes(allDifficultiesViewModel.textItem()) == 0)
    assert(view.displayedTimes(questionLoadingViewModel.textItem()) == 1)
  }

  test("Retries loading questions and asks the user if he wants to start on successful question loading") {
    val (view, dataAccess, program) = makeSut()
    view.stub("proceed")
    view.stub("1 3")
    view.stub("2")
    dataAccess.stubQuestions(None)
    dataAccess.stubQuestions(Some(List(Question("some text", List(), "some correct answer"))))

    program.unsafeRunSync()

    assert(view.displayedTimes(questionLoadingViewModel.textItem()) == 0)
    assert(view.displayedTimes(questionsReadyViewModel.textItem()) == 1)
  }

  test("Displays the first question and request the answer again on incorrect input") {
    val (view, dataAccess, program) = makeSut()
    stubInputsToStartTrivia(view)
    view.stub("incorrect input for the first question")
    view.stub("another incorrect input for the first question")
    dataAccess.stubQuestions(Some(List(firstQuestion)))

    program.unsafeRunSync()

    assert(view.displayedTimes(questionsReadyViewModel.textItem()) == 0)
    assert(view.containsInDisplayed("Progress:") == 1)
    assert(view.displayedTimes(mapToQuestionViewModel(firstQuestion).textItem()) == 1)
    assert(view.displayedTimes(answerInputErrorViewModel.textItem()) == 2)
  }

  test("Displays the second question on the first valid answer input") {
    val (view, dataAccess, program) = makeSut()
    stubInputsToStartTrivia(view)
    view.stub("incorrect input for the first question")
    view.stub("1")
    dataAccess.stubQuestions(Some(List(firstQuestion, secondQuestion)))

    program.unsafeRunSync()

    assert(view.displayedTimes(mapToQuestionViewModel(firstQuestion).textItem()) == 0)
    assert(view.displayedTimes(answerInputErrorViewModel.textItem()) == 0)
    assert(view.containsInDisplayed("Progress:") == 1)
    assert(view.displayedTimes(mapToQuestionViewModel(secondQuestion).textItem()) == 1)
  }

  test("Displays end game message when valid answers to all questions are given") {
    val (view, dataAccess, program) = makeSut()
    stubToCompleteTrivia(view, dataAccess)
    dataAccess.stubTime(0.0)
    dataAccess.stubTime(0.2)
    dataAccess.stubTime(0.0)
    dataAccess.stubTime(1.0)

    program.unsafeRunSync()
    
    assert(view.containsInDisplayed("Progress:") == 0)
    assert(view.displayedTimes(mapToQuestionViewModel(secondQuestion).textItem()) == 0)
    assert(view.containsInDisplayed("Your Score: 46") == 1)
  }

  test("Starts a new game after user provides any input after game is over") {
    val (view, dataAccess, program) = makeSut()
    stubToCompleteTrivia(view, dataAccess)
    view.stub("any")

    program.unsafeRunSync()

    assert(view.containsInDisplayed("Your Score") == 0)
    assert(view.displayedTimes(greetingViewModel.textItem()) == 1)
  }

  // MARK: Helpers

  private def makeSut(): (ViewSpy, DataAccessStub, IO[Unit]) = {
    val viewSpy = TextUISpy()
    val dataAccessStub = DataAccessStub()
    val trivia = composeTrivia(ui(viewSpy), dataAccess(dataAccessStub)).handleError(_ => ())
    (viewSpy, dataAccessStub, trivia)
  }

  private def ui(viewSpy: ViewSpy): UI = new UI {
    override def display(textItem: TextItem): IO[Unit] = viewSpy.display(textItem)
    override def clear: IO[Unit] = viewSpy.clear()
    override def input: IO[String] = viewSpy.input()
  }

  private def dataAccess(stub: DataAccessStub): DataAccess = new DataAccess:
    override def fetchQuestions(url: String): IO[Option[Questions]] = stub.fetchQuestions(url)
    override def measureTime: IO[Double] = stub.measureTime

  private def stubInputsToStartTrivia(view: ViewSpy): Unit = {
    view.stub("proceed")
    view.stub("1 3")
    view.stub("2")
    view.stub("")
  }

  private def stubToCompleteTrivia(view: ViewSpy, dataAccess: DataAccessStub): Unit = {
    stubInputsToStartTrivia(view)
    view.stub("1")
    view.stub("1")
    dataAccess.stubQuestions(Some(List(firstQuestion, secondQuestion)))
  }

  private def firstQuestion: Question = {
    Question("question text", List("answer 1", "answer 2"), "answer 2")
  }

  private def secondQuestion: Question = {
    Question("another question text", List("answer 3", "answer 4"), "answer 3")
  }
}