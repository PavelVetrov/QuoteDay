package com.example.quoteday.data.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface QuotesItemDao {

    @Query("SELECT*FROM quotes_item")
    fun getQuotesList(): LiveData<List<QuotesItemDbModal>>

    @Query("DELETE FROM quotes_item WHERE id=:quoteItemId")
    suspend fun deleteQuote(quoteItemId: Int)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addFavoriteQuote(quotesItemDbModal: QuotesItemDbModal)


}