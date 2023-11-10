package org.kyotskin.trivia
package presentation

case class TriviaResultsViewModel(
  title: String,
  metrics: List[String],
  feedback: String,
  farewellMessage: String
)
