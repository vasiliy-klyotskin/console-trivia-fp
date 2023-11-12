package org.kyotskin.trivia
package presentation

case class LeaderboardViewModel(
  title: String,
  items: List[String],
  yourPlace: String,
  message: String
)