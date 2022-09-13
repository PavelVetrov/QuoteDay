package com.example.quoteday.data.database

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [QuotesItemDbModal::class], version = 1)
abstract class AppDataBase : RoomDatabase() {

    abstract fun quotesItemDao(): QuotesItemDao

}