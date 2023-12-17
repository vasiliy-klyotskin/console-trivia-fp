package org.kyotskin.trivia
package presentation

import domain.FinalizedPlayer

case class LeaderboardViewModel(
  title: String,
  items: List[String],
  yourPlace: String,
  message: String
)

def mapToLeaderboardViewModel(players: List[FinalizedPlayer], current: FinalizedPlayer): LeaderboardViewModel = {
  val title = "Trivia Leaderboard"
  val message = "Keep playing and improving your knowledge!"
  val noOtherPlayers = !players.exists(_ != current)
  if (noOtherPlayers) {
    val yourPlace = s"Oh, it seems like there is no one other than you! Your highest score is ${current.topScore}"
    LeaderboardViewModel(title, List.empty, yourPlace, message)
  } else {
    val sortedPlayersByScore = players.sortBy(_.topScore)(Ordering[Int].reverse)
    val longestNameLength = players.maxBy(_.name.length).name.length
    val formatToItem = (player: FinalizedPlayer) => s"%-${longestNameLength}s   %d".format(player.name, player.topScore)
    val items = indexedList(sortedPlayersByScore.map(formatToItem))
    val placeNumber = sortedPlayersByScore.indexOf(current) + 1
    val yourPlace = s"Your place is $placeNumber with a score of ${current.topScore}"
    LeaderboardViewModel(title, items, yourPlace, message)
  }
}