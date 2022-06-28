package com.example.quoteday.domain

import com.example.quoteday.domain.model.Quotes
import retrofit2.Response

interface RepositoryQuotesDaily {

    suspend fun getQuoteDailyDto(): Response<Quotes>
}