package com.example.quoteday.domain.usecases

import com.example.quoteday.domain.RepositoryQuotes
import javax.inject.Inject

class GetFavoriteQuotesDbUseCase @Inject constructor(
    private val repositoryQuotes: RepositoryQuotes
) {

    operator fun invoke() = repositoryQuotes.getFavoriteQuotesDb()
}