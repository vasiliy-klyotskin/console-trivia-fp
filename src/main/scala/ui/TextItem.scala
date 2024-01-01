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
    vm.message + newLine + vm.categories.mkString(newLine) + newLine
  }
  
extension (vm: DifficultyChoiceViewModel)
  def textItem(): TextItem = {
    vm.message + newLine + vm.difficulties.mkString(newLine) + newLine
  }

extension (vm: QuestionLoadingViewModel)
  def textItem(): TextItem = {
    vm.text + newLine
  }

extension (vm: QuestionsReadyViewModel)
  def textItem(): TextItem = {
    vm.text + newLine
  }