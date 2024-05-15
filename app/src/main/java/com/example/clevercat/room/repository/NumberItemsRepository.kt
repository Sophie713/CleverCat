package com.example.clevercat.room.repository

import com.example.clevercat.dataClasses.NumberItem
import com.example.clevercat.room.NumberItemsDao
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class NumberItemsRepository @Inject constructor(
    private val dao: NumberItemsDao
) {

    fun upsertNumber(numberItem: NumberItem) {
        dao.upsertNumber(numberItem)
    }

    fun upsertItems(numbersList: List<NumberItem>) {
        dao.upsertItems(numbersList)
    }

    fun getAllNumbers(): List<NumberItem> {
        return dao.getNumbers()
    }

    fun deleteAllNumbers() {
        dao.deleteAllNumbers()
    }

    fun getLast(): NumberItem {
        return dao.getLast()
    }

    fun getNumbersCount() : Int {
        return dao.getItemsCount()
    }
}
