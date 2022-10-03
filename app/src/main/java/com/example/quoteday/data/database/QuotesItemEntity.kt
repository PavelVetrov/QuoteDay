package com.example.quoteday.data.database

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "quotes_table")
data class QuotesItemEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val quotes: String,
    val author: String
)
