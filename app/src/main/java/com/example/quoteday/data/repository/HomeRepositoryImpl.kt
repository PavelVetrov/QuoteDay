package com.example.quoteday.data.repository

import com.example.home.domain.repository.HomeRepository
import com.example.home.domain.entity.QuoteModelHome
import com.example.quoteday.data.database.QuotesDao
import com.example.quoteday.data.mappers.QuotesMapperHome
import com.example.quoteday.data.network.QuotesApi
import com.example.quoteday.domain.model.QuoteModel

import javax.inject.Inject

class HomeRepositoryImpl @Inject constructor(
    private val quotesMapperHome: QuotesMapperHome,
    private val quotesDao: QuotesDao,
    private val quotesApi: QuotesApi
): HomeRepository {

    override suspend fun requestQuoteDay(): QuoteModelHome {
        val quotesResponse = quotesApi.getQuoteDay().body()?.firstOrNull() ?: QuoteModel()
        return quotesMapperHome.mapQuotesToQuotesHome(quotesResponse)
    }

    override suspend fun addFavoriteQuote(quoteModel: QuoteModelHome) {
        quotesDao.addFavoriteQuote(quotesMapperHome.mapEntityToDbModal(quoteModel))
    }
}