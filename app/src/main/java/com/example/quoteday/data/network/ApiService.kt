package com.example.quoteday.data.network

import com.example.quoteday.domain.model.Quotes
import retrofit2.Response
import retrofit2.http.GET

interface ApiService {
    @GET(QUOTES_ITEM)
    suspend fun getQuoteList(): Response<Quotes>
    @GET(DAILY_QUOTES)
    suspend fun getQuoteDay(): Response<Quotes>

    companion object {
        private const val QUOTES_ITEM = "quotes"
        private const val DAILY_QUOTES = "today"
    }

}