package com.example.catalog.domain.usecase

import com.example.catalog.domain.repository.CatalogRepository
import com.example.catalog.domain.entity.QuoteModelCatalog
import javax.inject.Inject

class AddFavoriteQuoteUseCase @Inject constructor(
    private val catalogRepository: CatalogRepository
) {
    suspend operator fun invoke(quoteModel: QuoteModelCatalog) =
        catalogRepository.addFavoriteQuote(quoteModel)
}