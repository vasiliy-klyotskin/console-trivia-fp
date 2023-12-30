package org.kyotskin.trivia
package ui

import presentation.*

type TextItem = String

def newLine: String = "\n"
def clear: String = "\u001b[2J"

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
    vm.message + newLine + vm.categories
  }
  
extension (vm: DifficultyChoiceViewModel)
  def textItem(): TextItem = {
    vm.message + newLine + vm.difficulties
  }
