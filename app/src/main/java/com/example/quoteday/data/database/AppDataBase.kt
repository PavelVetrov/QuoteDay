package com.example.quoteday.data.database

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [QuotesItemEntity::class], version = 1)
abstract class AppDataBase : RoomDatabase() {

    abstract fun quotesDao(): QuotesDao

}