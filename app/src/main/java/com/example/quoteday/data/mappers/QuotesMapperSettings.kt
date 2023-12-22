package com.example.quoteday.data.mappers


import com.example.quoteday.domain.model.QuoteModel
import com.example.settings.domain.entity.QuoteModelSettings
import javax.inject.Inject

class QuotesMapperSettings @Inject constructor() {

    fun mapQuotesToQuotesSettings(quoteModel: QuoteModel) = QuoteModelSettings(
        author = quoteModel.author,
        quote = quoteModel.quote,
        id = quoteModel.id
    )
}