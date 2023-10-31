package org.kyotskin.trivia
import domain.baseScore
import domain.Answer
import org.scalatest.funsuite.AnyFunSuite

class BaseScoreTests extends AnyFunSuite {
  test("Base score equals zero on no answers") {
    assert(baseScore(List.empty) == 0)
  }

  test("Base score for one answer which hasn't been solved") {
    val answers = List(Answer(isCorrect = false, anyTime(), anyWordCount()))
    assert(baseScore(answers) == 0)
  }

  test("Base score for one answer which has been solved fast for 0 sec") {
    val answers = List(Answer(isCorrect = true, time = 0, wordsCountInQuestion = 16))
    assert(baseScore(answers) == 24)
  }

  test("Base score for one answer which has been solved fast for 1 sec") {
    val answers = List(Answer(isCorrect = true, time = 1, wordsCountInQuestion = 16))
    assert(baseScore(answers) == 21)
  }

  test("Base score for one answer which has been solved in maximum time that is considered fast") {
    val answers = List(Answer(isCorrect = true, time = 7, wordsCountInQuestion = 16))
    assert(baseScore(answers) == 3)
  }

  test("Base score for one answer which has been solved in the least time that is considered slow") {
    val answers = List(Answer(isCorrect = true, time = 10, wordsCountInQuestion = 16))
    assert(baseScore(answers) == 1)
  }

  test("Base score for one answer which has been solved slow") {
    val answers = List(Answer(isCorrect = true, time = 999, wordsCountInQuestion = 16))
    assert(baseScore(answers) == 1)
  }

  test("Base score for one answer which has been solved in normal time") {
    val answers = List(Answer(isCorrect = true, time = 8, wordsCountInQuestion = 16))
    assert(baseScore(answers) == 2)
  }

  test("Base score for two correct answers in a row of 2") {
    val answers = List(
      Answer(isCorrect = true, time = 6, wordsCountInQuestion = 16),
      Answer(isCorrect = true, time = 4, wordsCountInQuestion = 16)
    )
    // 6 + 15.6 = 21.6 => 21
    assert(baseScore(answers) == 21) 
  }

  test("Base score for three correct answers in a row of 3") {
    val answers = List(
      Answer(isCorrect = true, time = 4, wordsCountInQuestion = 16),
      Answer(isCorrect = true, time = 6, wordsCountInQuestion = 16),
      Answer(isCorrect = true, time = 5, wordsCountInQuestion = 16)
    )
    // 12 + 7,8 + 14,4 = 34,2 => 34
    assert(baseScore(answers) == 34)
  }

  test("Base score for answers with incorrect instances") {
    val answers = List(
      Answer(isCorrect = true, time = 4, wordsCountInQuestion = 16),
      Answer(isCorrect = true, time = 6, wordsCountInQuestion = 16),
      Answer(isCorrect = true, time = 5, wordsCountInQuestion = 16),
      Answer(isCorrect = false, time = anyTime(), wordsCountInQuestion = anyWordCount()),
      Answer(isCorrect = false, time = anyTime(), wordsCountInQuestion = anyWordCount()),
      Answer(isCorrect = false, time = anyTime(), wordsCountInQuestion = anyWordCount()),
      Answer(isCorrect = false, time = anyTime(), wordsCountInQuestion = anyWordCount())
    )
    // 12 + 7,8 + 14,4 = 34.2 => 34
    assert(baseScore(answers) == 34) 
  }

  test("Base score for multiple different answers") {
    val answers = List(
      Answer(isCorrect = true, time = 4, wordsCountInQuestion = 12),
      Answer(isCorrect = true, time = 6, wordsCountInQuestion = 16),
      Answer(isCorrect = true, time = 5, wordsCountInQuestion = 20),
      Answer(isCorrect = false, time = 5, wordsCountInQuestion = 4),
      Answer(isCorrect = true, time = 20, wordsCountInQuestion = 4),
      Answer(isCorrect = false, time = 5, wordsCountInQuestion = 4),
      Answer(isCorrect = true, time = 6, wordsCountInQuestion = 16),
      Answer(isCorrect = true, time = 3, wordsCountInQuestion = 4),
      Answer(isCorrect = true, time = 2, wordsCountInQuestion = 12),
      Answer(isCorrect = true, time = 6, wordsCountInQuestion = 20),
    )
    // 9 + 7.8 + 19.2 + 0 + 1 + 0 + 6 + 7.8 + 24 + 17.1 = 91.9 => 91
    assert(baseScore(answers) == 91) 
  }

  private def anyTime(): Double = 0.0
  
  private def anyWordCount(): Int = 0
}