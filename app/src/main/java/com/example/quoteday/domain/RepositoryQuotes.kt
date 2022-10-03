package com.example.quoteday.domain

import androidx.lifecycle.LiveData
import com.example.quoteday.domain.model.Quotes
import com.example.quoteday.domain.model.QuoteModel
import retrofit2.Response

interface RepositoryQuotes {

    suspend fun getQuotes(): Response<Quotes>

    suspend fun requestQuoteDay(): Response<Quotes>

    fun getFavoriteQuotes(): LiveData<List<QuoteModel>>

    suspend fun deleteFavoriteQuote(quoteModel: QuoteModel)

    suspend fun addFavoriteQuote(quoteModel: QuoteModel)
}