package com.example.home.domain.repository

import com.example.home.domain.entity.QuoteModelHome
import kotlinx.coroutines.flow.Flow


interface HomeRepository {
    suspend fun requestQuoteDay(): QuoteModelHome
    suspend fun addFavoriteQuote(quoteModel: QuoteModelHome)
    suspend fun deleteFavoriteQuote(quoteModel: QuoteModelHome)
    fun getQuotesListFavorite(): Flow<List<QuoteModelHome>>

}