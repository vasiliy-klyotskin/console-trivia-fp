package org.kyotskin.trivia
package presentation

import domain.Category
import domain.Category.*

case class CategoryChoiceViewModel(
  message: String,
  categories: List[String]
)

def mapToCategoryChoiceViewModel(categories: List[Category]): CategoryChoiceViewModel = {
  val message = "Great! Now that we know your name, it's time to choose the categories you'd like to play. You can select from a variety of categories. Please enter the category numbers you'd like to include, separated by spaces, or simply type 'all' to play with questions from all categories. Let's get started!"
  CategoryChoiceViewModel(message, indexedList(categories.map(nameFor)))
}

private def nameFor(category: Category): String = {
  category match {
    case Music => "Music"
    case SportAndLeisure => "Sport and Leisure"
    case FilmAndTV => "Film and TV"
    case ArtsAndLiterature => "Arts and Literature"
    case History => "History"
    case SocietyAndCulture => "Society and Culture"
    case Science => "Science"
    case Geography => "Geography"
    case FoodAndDrink => "Food and Drink"
    case GeneralKnowledge => "General Knowledge"
  }
}