package com.example.quoteday.domain

class GetQuoteDayDtoUseCase(
    private val repositoryQuotes: RepositoryQuotes
) {

    suspend operator fun invoke() = repositoryQuotes.getQuoteDayDto()
}