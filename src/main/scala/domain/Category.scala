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