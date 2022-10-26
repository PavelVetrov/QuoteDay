package com.example.quoteday.domain

import androidx.lifecycle.LiveData
import com.example.quoteday.domain.model.Quotes
import com.example.quoteday.domain.model.QuoteModel
import kotlinx.coroutines.flow.Flow
import retrofit2.Response

interface RepositoryQuotes {

    suspend fun getQuotes(): Response<Quotes>

    suspend fun requestQuoteDay(): Response<Quotes>

    fun getFavoriteQuotes(): Flow<List<QuoteModel>>

    suspend fun deleteFavoriteQuote(quoteModel: QuoteModel)

    suspend fun addFavoriteQuote(quoteModel: QuoteModel)
}