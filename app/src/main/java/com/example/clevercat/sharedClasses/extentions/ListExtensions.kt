package com.example.clevercat.sharedClasses.extentions

import com.example.clevercat.dataClasses.NumberItem

fun ArrayList<NumberItem>.getById(id: Int): NumberItem? {
    forEach {
        if (it.id == id) {
            return it
        }
    }
    return null
}