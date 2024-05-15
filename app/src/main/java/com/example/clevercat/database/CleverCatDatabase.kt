package com.example.clevercat.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.clevercat.dataClasses.NumberItem

@Database(
    entities = [NumberItem::class],
    version = 1,
    autoMigrations = [],
    exportSchema = true
)
abstract class CleverCatDatabase : RoomDatabase() {

    abstract fun numberItemsDao(): NumberItemsDao

    companion object {
        const val DATABASE_NAME = "CleverCatDatabase"
    }

}