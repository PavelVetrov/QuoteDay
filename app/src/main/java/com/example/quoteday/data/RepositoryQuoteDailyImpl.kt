package com.example.quoteday.data

import com.example.quoteday.data.network.ApiFactory
import com.example.quoteday.domain.RepositoryQuotesDaily
import com.example.quoteday.domain.model.Quotes
import retrofit2.Response

class RepositoryQuoteDailyImpl: RepositoryQuotesDaily {

    override suspend fun getQuoteDailyDto(): Response<Quotes> {
        return ApiFactory.apiService.getQuoteDaily()
    }
}