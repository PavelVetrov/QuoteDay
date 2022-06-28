package com.example.quoteday.domain

import com.example.quoteday.domain.RepositoryQuotes

class GetQuotesDailyUseCase(
    private val repositoryQuotesDaily: RepositoryQuotesDaily
) {

    suspend operator fun invoke() = repositoryQuotesDaily.getQuoteDailyDto()
}