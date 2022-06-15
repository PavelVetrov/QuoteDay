package com.example.quoteday.data

import com.example.quoteday.data.database.QuotesItemDbModal
import com.example.quoteday.domain.model.QuotesItem

class QuotesMapper {


  private  fun mapDbModalToEntity(dbModal: QuotesItemDbModal)= QuotesItem(
        a = dbModal.author,
        q = dbModal.quotes,
        c = dbModal.id
    )

    fun mapEntityToDbModal(quotesItem: QuotesItem) = QuotesItemDbModal(
        id = quotesItem.c,
        quotes = quotesItem.q,
        author = quotesItem.a
    )

    fun mapListDbModalToEntity(list: List<QuotesItemDbModal>) = list.map {

        mapDbModalToEntity(it)
    }
}