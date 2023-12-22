package com.example.settings.domain.usecase

import com.example.settings.domain.repository.SettingsRepository
import javax.inject.Inject

class GetQuoteDayUseCase @Inject constructor(
    private val repositoryQuotes: SettingsRepository
) {
    suspend operator fun invoke() = repositoryQuotes.requestQuoteDay()
}