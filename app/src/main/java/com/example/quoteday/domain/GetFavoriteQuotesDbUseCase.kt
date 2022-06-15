package com.example.quoteday.domain

import com.example.quoteday.domain.RepositoryQuotes

class GetFavoriteQuotesDbUseCase(
    private val repositoryQuotes: RepositoryQuotes
) {

    operator fun invoke() = repositoryQuotes.getFavoriteQuotesDb()
}