package com.example.quoteday.domain

import com.example.quoteday.domain.model.QuotesItem

class AddFavoriteQuoteUseCase(
    private val repositoryQuotes: RepositoryQuotes
) {

    suspend operator fun invoke(quotesItem: QuotesItem) =
        repositoryQuotes.addFavoriteQuote(quotesItem)
}