package com.example.quoteday.domain

import com.example.quoteday.domain.model.QuotesItem

class DeleteFavoriteQuoteUseCase(
    private val repositoryQuotes: RepositoryQuotes
) {

    suspend operator fun invoke(quotesItem: QuotesItem) =
        repositoryQuotes.deleteFavoriteQuote(quotesItem)
}