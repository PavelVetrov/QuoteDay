package com.example.home.domain.usecase

import com.example.home.domain.repository.HomeRepository
import com.example.home.domain.entity.QuoteModelHome
import javax.inject.Inject

class AddFavoriteQuoteUseCase @Inject constructor(
    private val repositoryQuotes: HomeRepository
) {
    suspend operator fun invoke(quoteModel: QuoteModelHome) =
        repositoryQuotes.addFavoriteQuote(quoteModel)
}