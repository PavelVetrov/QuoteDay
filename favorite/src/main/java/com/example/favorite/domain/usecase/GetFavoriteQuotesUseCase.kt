package com.example.favorite.domain.usecase

import com.example.favorite.domain.repository.FavoriteRepository
import javax.inject.Inject

class GetFavoriteQuotesUseCase @Inject constructor(
    private val favoriteRepository: FavoriteRepository
) {
    operator fun invoke() = favoriteRepository.getFavoriteQuotes()
}