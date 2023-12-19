package com.example.home.domain.usecase

import com.example.home.domain.repository.HomeRepository
import javax.inject.Inject

class GetQuoteDayUseCase @Inject constructor(
    private val repositoryQuotes: HomeRepository
) {
    suspend operator fun invoke() = repositoryQuotes.requestQuoteDay()
}