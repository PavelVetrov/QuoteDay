package com.example.quoteday.data.repository

import com.example.quoteday.data.mappers.QuotesMapperSettings
import com.example.quoteday.data.network.QuotesApi
import com.example.quoteday.domain.model.QuoteModel
import com.example.settings.domain.entity.QuoteModelSettings
import com.example.settings.domain.repository.SettingsRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class SettingsRepositoryImpl @Inject constructor(
    private val quotesMapperSettings: QuotesMapperSettings,
    private val quotesApi: QuotesApi
): SettingsRepository {

    override suspend fun requestQuoteDay(): QuoteModelSettings = withContext(Dispatchers.IO) {
        val quotesResponse = quotesApi.getQuoteDay().body()?.firstOrNull() ?: QuoteModel()
        quotesMapperSettings.mapQuotesToQuotesSettings(quotesResponse)
    }
}