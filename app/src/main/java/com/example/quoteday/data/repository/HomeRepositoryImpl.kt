package com.example.quoteday.data.repository

import com.example.home.domain.repository.HomeRepository
import com.example.home.domain.entity.QuoteModelHome
import com.example.quoteday.data.database.QuotesDao
import com.example.quoteday.data.mappers.QuotesMapperHome
import com.example.quoteday.data.network.QuotesApi
import com.example.quoteday.domain.model.QuoteModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext

import javax.inject.Inject

class HomeRepositoryImpl @Inject constructor(
    private val quotesMapperHome: QuotesMapperHome,
    private val quotesDao: QuotesDao,
    private val quotesApi: QuotesApi
): HomeRepository {

    override suspend fun requestQuoteDay(): QuoteModelHome = withContext(Dispatchers.IO) {
        val quotesResponse = quotesApi.getQuoteDay().body()?.firstOrNull() ?: QuoteModel()
        quotesMapperHome.mapQuotesToQuotesHome(quotesResponse)
    }
    override suspend fun addFavoriteQuote(quoteModel: QuoteModelHome) {
        quotesDao.addFavoriteQuote(quotesMapperHome.mapEntityToDbModal(quoteModel))
    }

    override suspend fun deleteFavoriteQuote(quoteModel: QuoteModelHome) {
        quotesDao.deleteQuote(quoteModel.quote)
    }

    override fun getQuotesListFavorite(): Flow<List<QuoteModelHome>> {
        return quotesDao.getQuotesList().map {
            quotesMapperHome.mapListDbModalToEntity(it)
        }
    }
}