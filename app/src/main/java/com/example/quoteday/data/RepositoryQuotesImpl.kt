package com.example.quoteday.data

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import com.example.quoteday.data.database.AppDataBase
import com.example.quoteday.data.network.ApiFactory
import com.example.quoteday.domain.RepositoryQuotes
import com.example.quoteday.domain.model.Quotes
import com.example.quoteday.domain.model.QuotesItem
import retrofit2.Response

class RepositoryQuotesImpl(application: Application) : RepositoryQuotes {

    private val mapper = QuotesMapper()
    private val quotesDao = AppDataBase.getInstance(application).quotesItemDao()

    override suspend fun getQuotesListDto(): Response<Quotes> {
        return ApiFactory.apiService.getQuoteList()
    }

    override suspend fun getQuoteDayDto(): Response<Quotes> {
        return ApiFactory.apiService.getQuoteDay()
    }

    override fun getFavoriteQuotesDb(): LiveData<List<QuotesItem>> =
        Transformations.map(quotesDao.getQuotesList()) {
            mapper.mapListDbModalToEntity(it)
        }

    override suspend fun deleteFavoriteQuote(quotesItem: QuotesItem) {
        quotesDao.deleteQuote(quotesItem.c)
    }

    override suspend fun addFavoriteQuote(quotesItem: QuotesItem) {
        quotesDao.addFavoriteQuote(mapper.mapEntityToDbModal(quotesItem))
    }

}