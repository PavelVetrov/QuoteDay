package com.example.quoteday.domain

class GetQuotesListDtoUseCase(
    private val repositoryQuotes: RepositoryQuotes
) {

  suspend operator fun  invoke() = repositoryQuotes.getQuotesListDto()
}