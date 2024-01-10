package com.example.quoteday.data.repository

import com.example.favorite.domain.entity.QuoteModalFavorite
import com.example.favorite.domain.repository.FavoriteRepository
import com.example.quoteday.data.database.QuotesDao
import com.example.quoteday.data.mappers.QuotesMapperFavorite
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class FavoriteRepositoryImpl @Inject constructor(
    private val quotesMapperFavorite: QuotesMapperFavorite,
    private val quotesDao: QuotesDao,
): FavoriteRepository {

    override fun getFavoriteQuotes(): Flow<List<QuoteModalFavorite>> {
        return quotesDao.getQuotesList().map {
            quotesMapperFavorite.mapListDbModalToEntity(it)
        }
    }

    override suspend fun deleteFavoriteQuote(quoteModel: QuoteModalFavorite) {
        quotesDao.deleteQuoteById(quoteModel.id)
    }
}