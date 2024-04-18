package com.example.clevercat.activityMain.iterfaces

import com.example.clevercat.dataClasses.NumberItem

interface GameOperationsInterface {

    fun saveGame(numbersArray: ArrayList<NumberItem>)
    fun loadGame()
    fun resetGame()
}