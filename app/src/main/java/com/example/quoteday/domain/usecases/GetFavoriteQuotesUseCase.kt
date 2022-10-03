package com.example.quoteday.domain.usecases

import com.example.quoteday.domain.RepositoryQuotes
import javax.inject.Inject

class GetFavoriteQuotesUseCase @Inject constructor(
    private val repositoryQuotes: RepositoryQuotes
) {
    operator fun invoke() = repositoryQuotes.getFavoriteQuotes()
}