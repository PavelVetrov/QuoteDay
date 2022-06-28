package com.example.quoteday.data.network

import com.example.quoteday.domain.model.Quotes
import com.example.quoteday.domain.model.QuotesItem
import retrofit2.Response
import retrofit2.http.GET

interface ApiService {

    @GET("quotes")
    suspend fun getQuoteList():Response<Quotes>

    @GET("today")
    suspend fun getQuoteDay(): Response<Quotes>

    @GET("today")
    suspend fun getQuoteDaily(): Response<Quotes>
}