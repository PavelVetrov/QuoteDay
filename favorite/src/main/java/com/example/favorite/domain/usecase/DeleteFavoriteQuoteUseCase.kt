package com.example.favorite.domain.usecase

import com.example.favorite.domain.entity.QuoteModalFavorite
import com.example.favorite.domain.repository.FavoriteRepository
import javax.inject.Inject

class DeleteFavoriteQuoteUseCase @Inject constructor(
    private val favoriteRepository: FavoriteRepository
) {
    suspend operator fun invoke(quoteModel: QuoteModalFavorite) =
        favoriteRepository.deleteFavoriteQuote(quoteModel)
}