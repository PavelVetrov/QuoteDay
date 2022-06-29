package com.example.quoteday.domain

class GetQuotesDailyUseCase(
    private val repositoryQuotesDaily: RepositoryQuotesDaily
) {

    suspend operator fun invoke() = repositoryQuotesDaily.getQuoteDailyDto()
}