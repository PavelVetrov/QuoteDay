package com.example.home.domain.usecase

import com.example.home.domain.repository.HomeRepository
import javax.inject.Inject

class GetHomeFavoriteQuotesUseCase @Inject constructor(
    private val homeRepository: HomeRepository
) {
    operator fun invoke() = homeRepository.getQuotesListFavorite()
}