package com.example.quoteday.data

import com.example.quoteday.data.database.QuotesItemDbModal
import com.example.quoteday.domain.model.QuotesItem
import javax.inject.Inject

class QuotesMapper @Inject constructor() {

    private fun mapDbModalToEntity(dbModal: QuotesItemDbModal) = QuotesItem(
        author = dbModal.author,
        quotes = dbModal.quotes,
        id = dbModal.id
    )
    fun mapEntityToDbModal(quotesItem: QuotesItem) = QuotesItemDbModal(
            id = quotesItem.id,
            quotes = quotesItem.quotes,
            author = quotesItem.author
    )
    fun mapListDbModalToEntity(list: List<QuotesItemDbModal>) = list.map {
        mapDbModalToEntity(it)
    }
}