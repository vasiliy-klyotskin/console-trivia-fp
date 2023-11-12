package org.kyotskin.trivia
package presentation

import domain.*
import org.scalatest.funsuite.AnyFunSuite

class MapToLeaderboardViewModelTests extends AnyFunSuite {
  test("Sut maps empty player models to view model") {
    val current = Player("John Connor", 2350)
    val result = mapToLeaderboardViewModel(players = List.empty, current)
    val expected = LeaderboardViewModel(
      title = "Trivia Leaderboard",
      items = List.empty,
      yourPlace = "Oh, it seems like there is no one other than you! Your highest score is 2350",
      message = "Keep playing and improving your knowledge!"
    )

    assert(result == expected)
  }

  test("Sut maps player models that consist of just current player model to view model") {
    val current = Player("John Connor", 2350)
    val result = mapToLeaderboardViewModel(players = List(current), current)
    val expected = LeaderboardViewModel(
      title = "Trivia Leaderboard",
      items = List.empty,
      yourPlace = "Oh, it seems like there is no one other than you! Your highest score is 2350",
      message = "Keep playing and improving your knowledge!"
    )

    assert(result == expected)
  }

  test("Sut maps player models model to view model") {
    val players = List(Player("Ivan Ivanov", 6735), Player("Petya Petrov", 7910), Player("Anton Antonov", 1150), Player("John Connor", 2350))
    val current = Player("John Connor", 2350)
    val result = mapToLeaderboardViewModel(players, current)
    val expected = LeaderboardViewModel(
      title = "Trivia Leaderboard",
      items = List(
        "1. Petya Petrov    7910",
        "2. Ivan Ivanov     6735",
        "3. John Connor     2350",
        "4. Anton Antonov   1150"
      ),
      yourPlace = "Your place is 3 with a score of 2350",
      message = "Keep playing and improving your knowledge!"
    )

    assert(result == expected)
  }
}