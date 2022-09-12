package com.example.quoteday.data.database

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "quotes_item")
data class QuotesItemDbModal(
    @PrimaryKey
    val id: Int,
    val quotes: String,
    val author: String
)
