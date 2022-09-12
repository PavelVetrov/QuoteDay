package com.example.quoteday.domain.usecases

import com.example.quoteday.domain.RepositoryQuotes
import com.example.quoteday.domain.model.QuotesItem
import javax.inject.Inject

class DeleteFavoriteQuoteUseCase @Inject constructor(
    private val repositoryQuotes: RepositoryQuotes
) {
    suspend operator fun invoke(quotesItem: QuotesItem) =
        repositoryQuotes.deleteFavoriteQuote(quotesItem)
}