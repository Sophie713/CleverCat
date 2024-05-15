package com.example.clevercat.iterfaces

import com.example.clevercat.dataClasses.NumberItem

interface GameOperationsInterface {

    fun saveGame(numbersArray: ArrayList<NumberItem>)
    fun loadGame()
    fun resetGame()
}