package com.example.quoteday.domain

import javax.inject.Inject

class GetQuoteDayDtoUseCase @Inject constructor(
    private val repositoryQuotes: RepositoryQuotes
) {

    suspend operator fun invoke() = repositoryQuotes.getQuoteDayDto()
}