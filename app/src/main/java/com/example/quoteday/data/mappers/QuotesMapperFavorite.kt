package com.example.quoteday.data.mappers

import com.example.favorite.domain.entity.QuoteModalFavorite
import com.example.quoteday.data.database.QuotesItemEntity
import javax.inject.Inject

class QuotesMapperFavorite @Inject constructor() {

    private fun mapDbModalToEntity(dbModal: QuotesItemEntity) = QuoteModalFavorite(
        author = dbModal.author,
        quote = dbModal.quotes,
        id = dbModal.id
    )

    fun mapListDbModalToEntity(list: List<QuotesItemEntity>) = list.map {
        mapDbModalToEntity(it)
    }
}