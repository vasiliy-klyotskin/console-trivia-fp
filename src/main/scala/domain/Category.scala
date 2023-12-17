package org.kyotskin.trivia
package domain

import domain.Category.*

enum Category {
  case Music
  case SportAndLeisure
  case FilmAndTV
  case ArtsAndLiterature
  case History
  case SocietyAndCulture
  case Science
  case Geography
  case FoodAndDrink
  case GeneralKnowledge
}

enum CategoryChoice {
  case Specific(categories: List[Category])
  case All
}

def allCategories(): List[Category] = {
  List(
    Science,
    Music,
    FilmAndTV,
    ArtsAndLiterature,
    History,
    Geography,
    FoodAndDrink,
    SocietyAndCulture,
    SportAndLeisure,
    GeneralKnowledge
  )
}