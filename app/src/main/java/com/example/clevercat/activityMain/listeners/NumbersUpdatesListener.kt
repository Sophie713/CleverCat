package com.sophie.miller.clevercat.listener

import com.example.clevercat.dataClasses.NumberItem

interface NumbersUpdatesListener {
    fun updateNumber(numberItem: NumberItem)
    fun removeNumber(index: Int)
    fun scrollToPosition(position: Int)
}