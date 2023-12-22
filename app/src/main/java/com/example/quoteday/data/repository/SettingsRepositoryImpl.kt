package com.example.quoteday.data.repository

import com.example.quoteday.data.mappers.QuotesMapperSettings
import com.example.quoteday.data.network.QuotesApi
import com.example.quoteday.domain.model.QuoteModel
import com.example.settings.domain.entity.QuoteModelSettings
import com.example.settings.domain.repository.SettingsRepository
import javax.inject.Inject

class SettingsRepositoryImpl @Inject constructor(
    private val quotesMapperSettings: QuotesMapperSettings,
    private val quotesApi: QuotesApi
): SettingsRepository {
    override suspend fun requestQuoteDay(): QuoteModelSettings {
        val quotesResponse = quotesApi.getQuoteDay().body()?.firstOrNull() ?: QuoteModel()
        return quotesMapperSettings.mapQuotesToQuotesSettings(quotesResponse)
    }
}