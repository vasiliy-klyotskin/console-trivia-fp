package org.kyotskin.trivia
package presentation

import domain.*

case class DifficultyChoiceViewModel(
  message: String,
  difficulties: List[String]
)

def mapToDifficultyChoiceViewModel(difficulties: List[Difficulty]): DifficultyChoiceViewModel = {
  val message = s"Fantastic, You've selected your categories. Now, it's time to choose the difficulty level for your trivia adventure. Please enter the number corresponding to your desired difficulty level:"
  DifficultyChoiceViewModel(message, indexedList(difficulties.map(mapDifficultyName)))
}

def allDifficultiesViewModel: DifficultyChoiceViewModel = {
  mapToDifficultyChoiceViewModel(Difficulty.values.toList)
}

private def mapDifficultyName(difficulty: Difficulty): String = {
  difficulty match {
    case Difficulty.Easy => "Easy"
    case Difficulty.Medium => "Medium"
    case Difficulty.Hard => "Hard"
  }
}