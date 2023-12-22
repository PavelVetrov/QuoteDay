package com.example.settings.domain.repository

import com.example.settings.domain.entity.QuoteModelSettings

interface SettingsRepository {
    suspend fun requestQuoteDay(): QuoteModelSettings
}