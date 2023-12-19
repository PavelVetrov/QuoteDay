package com.example.quoteday.data.mappers

import com.example.home.domain.entity.QuoteModelHome
import com.example.quoteday.data.database.QuotesItemEntity
import com.example.quoteday.domain.model.QuoteModel
import javax.inject.Inject

class QuotesMapperHome @Inject constructor() {

    fun mapEntityToDbModal(quoteModel: QuoteModelHome) = QuotesItemEntity(
        quotes = quoteModel.quote,
        author = quoteModel.author
    )

    fun mapQuotesToQuotesHome(quoteModel: QuoteModel) = QuoteModelHome(
        author = quoteModel.author,
        quote = quoteModel.quote,
        id = quoteModel.id
    )
}