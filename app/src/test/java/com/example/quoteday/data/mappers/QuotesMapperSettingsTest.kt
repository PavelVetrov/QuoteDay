package com.example.quoteday.data.mappers

import com.example.quoteday.domain.model.QuoteModel
import com.example.settings.domain.entity.QuoteModelSettings
import org.junit.Assert.*

import org.junit.Test

class QuotesMapperSettingsTest {

    @Test
    fun mapQuotesToQuotesSettings() {
        val quotesMapperSettings = QuotesMapperSettings()
        val quoteModel = QuoteModel(
            id = 0,
            author = "some author",
            quote = "some quote"
        )
        val mapQuotesToQuotesSettingsValue =
            quotesMapperSettings.mapQuotesToQuotesSettings(quoteModel)
        val quoteModelSettings = QuoteModelSettings(
            id = 0,
            author = "some author",
            quote = "some quote"
        )

        assertEquals(mapQuotesToQuotesSettingsValue, quoteModelSettings)
    }
}