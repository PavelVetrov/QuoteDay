package com.example.quoteday.domain

import androidx.lifecycle.LiveData
import com.example.quoteday.domain.model.Quotes
import com.example.quoteday.domain.model.QuotesItem
import retrofit2.Response

interface RepositoryQuotes {

    suspend fun getQuotesListDto(): Response<Quotes>

    suspend fun getQuoteDayDto(): Response<Quotes>

    fun getFavoriteQuotesDb(): LiveData<List<QuotesItem>>

    suspend fun deleteFavoriteQuote(quotesItem: QuotesItem)

    suspend fun addFavoriteQuote(quotesItem: QuotesItem)

}