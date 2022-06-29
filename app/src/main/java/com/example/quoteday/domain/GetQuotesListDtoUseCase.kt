package com.example.quoteday.domain

import javax.inject.Inject

class GetQuotesListDtoUseCase @Inject constructor(
    private val repositoryQuotes: RepositoryQuotes
) {

    suspend operator fun invoke() = repositoryQuotes.getQuotesListDto()
}