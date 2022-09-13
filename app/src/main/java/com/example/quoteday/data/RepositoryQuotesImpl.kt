package com.example.quoteday.data

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import com.example.quoteday.data.database.QuotesItemDao
import com.example.quoteday.data.network.QuotesApi
import com.example.quoteday.domain.RepositoryQuotes
import com.example.quoteday.domain.model.Quotes
import com.example.quoteday.domain.model.QuotesItem
import retrofit2.Response
import javax.inject.Inject

class RepositoryQuotesImpl @Inject constructor(
    private val mapper: QuotesMapper,
    private val quotesDao: QuotesItemDao,
    private val quotesApi: QuotesApi
) : RepositoryQuotes {

    override suspend fun getQuotesListDto(): Response<Quotes> {
        return quotesApi.getQuoteList()
    }
    override suspend fun getQuoteDayDto(): Response<Quotes> {
        return quotesApi.getQuoteDay()
    }
    override fun getFavoriteQuotesDb(): LiveData<List<QuotesItem>> =
        Transformations.map(quotesDao.getQuotesList()) {
            mapper.mapListDbModalToEntity(it)
        }
    override suspend fun deleteFavoriteQuote(quotesItem: QuotesItem) {
        quotesDao.deleteQuote(quotesItem.id)
    }
    override suspend fun addFavoriteQuote(quotesItem: QuotesItem) {
        quotesDao.addFavoriteQuote(mapper.mapEntityToDbModal(quotesItem))
    }
}