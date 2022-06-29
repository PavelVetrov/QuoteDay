package com.example.quoteday.domain

class GetFavoriteQuotesDbUseCase(
    private val repositoryQuotes: RepositoryQuotes
) {

    operator fun invoke() = repositoryQuotes.getFavoriteQuotesDb()
}