package com.example.quoteday.data.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface QuotesDao {

    @Query("SELECT * FROM quotes_table")
    fun getQuotesList(): Flow<List<QuotesItemEntity>>

    @Query("DELETE FROM quotes_table WHERE id=:quoteItemId")
    suspend fun deleteQuote(quoteItemId: Int)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addFavoriteQuote(quotesItemEntity: QuotesItemEntity)

}