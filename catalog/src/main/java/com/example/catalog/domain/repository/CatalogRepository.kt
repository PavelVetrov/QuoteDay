package com.example.catalog.domain.repository

import com.example.catalog.domain.entity.QuoteModelCatalog
import com.example.catalog.domain.entity.QuotesCatalog

interface CatalogRepository {
    suspend fun addFavoriteQuote(quoteModel: QuoteModelCatalog)
    suspend fun getQuotes(): QuotesCatalog
}