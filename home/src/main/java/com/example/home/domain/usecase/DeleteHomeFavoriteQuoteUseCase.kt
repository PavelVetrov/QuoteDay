package com.example.home.domain.usecase

import com.example.home.domain.entity.QuoteModelHome
import com.example.home.domain.repository.HomeRepository
import javax.inject.Inject

class DeleteHomeFavoriteQuoteUseCase @Inject constructor(
    private val repositoryQuotes: HomeRepository
) {
    suspend operator fun invoke(quoteModel: QuoteModelHome) =
        repositoryQuotes.deleteFavoriteQuote(quoteModel)
}