package com.example.home.domain.repository

import com.example.home.domain.entity.QuoteModelHome


interface HomeRepository {

    suspend fun requestQuoteDay(): QuoteModelHome
    suspend fun addFavoriteQuote(quoteModel: QuoteModelHome)

}