package com.example.quoteday.domain.usecases

import com.example.quoteday.domain.RepositoryQuotes
import com.example.quoteday.domain.model.QuoteModel
import javax.inject.Inject

class DeleteFavoriteQuoteUseCase @Inject constructor(
    private val repositoryQuotes: RepositoryQuotes
) {
    suspend operator fun invoke(quoteModel: QuoteModel) =
        repositoryQuotes.deleteFavoriteQuote(quoteModel)
}