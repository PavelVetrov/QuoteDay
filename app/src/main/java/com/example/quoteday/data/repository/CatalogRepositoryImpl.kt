package com.example.quoteday.data.repository

import com.example.catalog.domain.repository.CatalogRepository
import com.example.catalog.domain.entity.QuoteModelCatalog
import com.example.catalog.domain.entity.QuotesCatalog
import com.example.quoteday.data.database.QuotesDao
import com.example.quoteday.data.mappers.QuotesMapperCatalog
import com.example.quoteday.data.network.QuotesApi
import com.example.quoteday.domain.model.Quotes
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class CatalogRepositoryImpl @Inject constructor(
    private val quotesMapperCatalog: QuotesMapperCatalog,
    private val quotesDao: QuotesDao,
    private val quotesApi: QuotesApi
): CatalogRepository {

    override suspend fun addFavoriteQuote(quoteModel: QuoteModelCatalog) {
        quotesDao.addFavoriteQuote(quotesMapperCatalog.mapEntityToDbModal(quoteModel))
    }
    override suspend fun getQuotes(): QuotesCatalog = withContext(Dispatchers.IO) {
        val quotesList = quotesApi.getQuoteList().body() ?: Quotes(emptyList())
        quotesMapperCatalog.mapQuotesToQuotesCatalog(quotesList)
    }
}