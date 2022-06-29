package com.example.quoteday.domain

import javax.inject.Inject

class GetFavoriteQuotesDbUseCase @Inject constructor(
    private val repositoryQuotes: RepositoryQuotes
) {

    operator fun invoke() = repositoryQuotes.getFavoriteQuotesDb()
}