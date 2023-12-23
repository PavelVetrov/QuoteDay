package com.example.favorite.domain.repository

import com.example.favorite.domain.entity.QuoteModalFavorite
import kotlinx.coroutines.flow.Flow

interface FavoriteRepository {

    fun getFavoriteQuotes(): Flow<List<QuoteModalFavorite>>
    suspend fun deleteFavoriteQuote(quoteModel: QuoteModalFavorite)
}