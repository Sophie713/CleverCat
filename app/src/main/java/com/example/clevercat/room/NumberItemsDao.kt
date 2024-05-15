package com.example.clevercat.room

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import com.example.clevercat.dataClasses.NumberItem
import com.example.clevercat.sharedClasses.constants.Constants.NUMBERS_TABLE_NAME
import dagger.Provides
import kotlinx.coroutines.flow.Flow

@Dao
interface NumberItemsDao {
    @Upsert
    fun upsertNumber(numberItem: NumberItem)

    @Upsert
    fun upsertItems(numbersList: List<NumberItem>)

    @Query("SELECT * FROM $NUMBERS_TABLE_NAME")
    fun getNumbers(): List<NumberItem>

    @Query("DELETE FROM $NUMBERS_TABLE_NAME")
    fun deleteAllNumbers()

    @Query("SELECT * FROM $NUMBERS_TABLE_NAME ORDER BY id DESC LIMIT 1")
    fun getLast(): NumberItem

    @Query("SELECT COUNT(*) FROM $NUMBERS_TABLE_NAME")
    fun getItemsCount(): Int
}
