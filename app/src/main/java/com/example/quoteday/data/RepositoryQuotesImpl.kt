package com.example.quoteday.data

import com.example.quoteday.data.database.QuotesDao
import com.example.quoteday.data.mappers.QuotesMapper
import com.example.quoteday.data.network.QuotesApi
import com.example.quoteday.domain.RepositoryQuotes
import com.example.quoteday.domain.model.QuoteModel
import com.example.quoteday.domain.model.Quotes
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext
import retrofit2.Response
import javax.inject.Inject

class RepositoryQuotesImpl @Inject constructor(
    private val mapper: QuotesMapper,
    private val quotesDao: QuotesDao,
    private val quotesApi: QuotesApi
) : RepositoryQuotes {

    override suspend fun getQuotes(): Response<Quotes> = withContext(Dispatchers.IO) {
        quotesApi.getQuoteList()
    }

    override suspend fun requestQuoteDay(): Response<Quotes> = withContext(Dispatchers.IO) {
        quotesApi.getQuoteDay()
    }

    override fun getFavoriteQuotes(): Flow<List<QuoteModel>> {
        return quotesDao.getQuotesList().map {
            mapper.mapListDbModalToEntity(it)
        }
    }

    override suspend fun deleteFavoriteQuote(quoteModel: QuoteModel) {
        quotesDao.deleteQuote(quoteModel.id)
    }

    override suspend fun addFavoriteQuote(quoteModel: QuoteModel) {
        quotesDao.addFavoriteQuote(mapper.mapEntityToDbModal(quoteModel))
    }
}