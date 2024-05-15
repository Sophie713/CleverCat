package com.example.clevercat.iterfaces

import com.example.clevercat.dataClasses.NumberItem

interface GameplayInterface {

    fun showHint() : Int?

    fun addNumbers(numberOfRows: Int)

    fun clickNumber(number: NumberItem)
}