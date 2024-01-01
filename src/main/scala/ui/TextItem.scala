package org.kyotskin.trivia
package ui

import presentation.*

type TextItem = String

def newLine: String = "\n"
def clearItem: String = "\u001b[2J"

extension (vm: GreetingViewModel)
  def textItem(): TextItem = {
    vm.greeting + newLine + newLine + vm.nameRequest + newLine
  }


extension (vm: ErrorViewModel)
  def textItem(): TextItem = {
    vm.text + newLine
  }
  
extension (vm: CategoryChoiceViewModel)
  def textItem(): TextItem = {
    vm.message + newLine + newLine + vm.categories.mkString(newLine) + newLine + newLine
  }
  
extension (vm: DifficultyChoiceViewModel)
  def textItem(): TextItem = {
    vm.message + newLine + newLine + vm.difficulties.mkString(newLine) + newLine + newLine
  }

extension (vm: QuestionLoadingViewModel)
  def textItem(): TextItem = {
    vm.text + newLine + newLine
  }

extension (vm: QuestionsReadyViewModel)
  def textItem(): TextItem = {
    vm.text + newLine + newLine
  }

extension (vm: StatusViewModel)
  def textItem(): TextItem = {
    vm.progressTitle + newLine + vm.scoreTitle + newLine + vm.progressIndicator + newLine + newLine
  }

extension (vm: QuestionViewModel)
  def textItem(): TextItem = {
    vm.question + newLine + newLine + vm.possibleAnswers.mkString(newLine) + newLine + newLine
  }
  
extension (vm: TriviaResultsViewModel)
  def textItem(): TextItem = {
    vm.title + newLine + newLine + 
    vm.metrics.mkString(newLine) + newLine + newLine + 
    vm.feedback + newLine + newLine +
    vm.farewellMessage + newLine + newLine
  }
  
extension (vm: LeaderboardViewModel)
  def textItem(): TextItem = {
    vm.title + newLine + newLine + 
    vm.items.mkString(newLine) + newLine + newLine +
    vm.yourPlace + newLine + newLine +
    vm.message + newLine + newLine
  }