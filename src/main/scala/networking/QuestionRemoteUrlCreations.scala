package org.kyotskin.trivia
package networking

import domain.*
import Category.*
import CategoryChoice.*

def questionsUrl(difficulty: Difficulty, categoriesChoice: CategoryChoice): String = {
  val difficultyQuery = s"&difficulties=${difficulty.toString.toLowerCase}"
  val categoriesQuery = categoriesChoice match {
    case All => ""
    case Specific(categories) => "&categories=" + categories.map(remoteCategory).mkString(",")
  }
  "https://the-trivia-api.com/v2/questions?limit=20" + difficultyQuery + categoriesQuery
}

private def remoteCategory(category: Category): String = {
  category match {
    case Music => "music"
    case SportAndLeisure => "sport_and_leisure"
    case FilmAndTV => "film_and_tv"
    case ArtsAndLiterature => "arts_and_literature"
    case History => "history"
    case SocietyAndCulture => "society_and_culture"
    case Science => "science"
    case Geography => "geography"
    case FoodAndDrink => "food_and_drink"
    case GeneralKnowledge => "general_knowledge"
  }
}