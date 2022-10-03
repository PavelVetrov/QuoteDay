package com.example.quoteday.data

import com.example.quoteday.data.database.QuotesItemEntity
import com.example.quoteday.domain.model.QuoteModel
import javax.inject.Inject

class QuotesMapper @Inject constructor() {

    private fun mapDbModalToEntity(dbModal: QuotesItemEntity) = QuoteModel(
        author = dbModal.author,
        quote = dbModal.quotes,
        id = dbModal.id
    )

    fun mapEntityToDbModal(quoteModel: QuoteModel) = QuotesItemEntity(
        quotes = quoteModel.quote,
        author = quoteModel.author
    )

    fun mapListDbModalToEntity(list: List<QuotesItemEntity>) = list.map {
        mapDbModalToEntity(it)
    }
}