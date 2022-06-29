package com.example.quoteday.domain

import com.example.quoteday.domain.model.QuotesItem
import javax.inject.Inject

class AddFavoriteQuoteUseCase @Inject constructor(
    private val repositoryQuotes: RepositoryQuotes
) {

    suspend operator fun invoke(quotesItem: QuotesItem) =
        repositoryQuotes.addFavoriteQuote(quotesItem)
}