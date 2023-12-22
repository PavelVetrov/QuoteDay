package com.example.quoteday.data.mappers

import com.example.catalog.domain.entity.QuoteModelCatalog
import com.example.catalog.domain.entity.QuotesCatalog
import com.example.quoteday.data.database.QuotesItemEntity
import com.example.quoteday.domain.model.Quotes
import javax.inject.Inject

class QuotesMapperCatalog @Inject constructor() {

    fun mapEntityToDbModal(quoteModel: QuoteModelCatalog) = QuotesItemEntity(
        quotes = quoteModel.quote,
        author = quoteModel.author
    )

    fun mapQuotesToQuotesCatalog(quoteModel: Quotes): QuotesCatalog {
        return quoteModel.mapTo(QuotesCatalog()) {
            QuoteModelCatalog(
                author = it.author,
                quote = it.quote,
                id = it.id
            )
        }
    }
}