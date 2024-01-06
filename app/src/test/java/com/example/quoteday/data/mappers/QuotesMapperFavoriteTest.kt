package com.example.quoteday.data.mappers

import com.example.favorite.domain.entity.QuoteModalFavorite
import com.example.quoteday.data.database.QuotesItemEntity
import org.junit.Assert.*
import org.junit.Before

import org.junit.Test

class QuotesMapperFavoriteTest {

    private lateinit var quotesMapperFavorite: QuotesMapperFavorite

    @Before
    fun before() {
        quotesMapperFavorite = QuotesMapperFavorite()
    }

    @Test
    fun mapDbModalToEntity() {
        val quotesItemEntity = QuotesItemEntity(
            id = 0,
            author = "some author",
            quotes = "some quotes"
        )
        val mapperDbModalToEntity = quotesMapperFavorite.mapDbModalToEntity(quotesItemEntity)
        val quoteModalFavorite = QuoteModalFavorite(
            id = 0,
            author = "some author",
            quote = "some quotes"
        )

        assertEquals(mapperDbModalToEntity, quoteModalFavorite)
    }

    @Test
    fun mapListDbModalToEntity() {
        val quotesItemEntity = QuotesItemEntity(
            id = 0,
            author = "some author",
            quotes = "some quotes"
        )
        val mapperDbModalToEntity =
            listOf(quotesMapperFavorite.mapDbModalToEntity(quotesItemEntity))
        val quotesItemEntityList = listOf(quotesItemEntity)
        val mapListDbModalToEntityValue =
            quotesMapperFavorite.mapListDbModalToEntity(quotesItemEntityList)
        assertEquals(mapListDbModalToEntityValue, mapperDbModalToEntity)
    }
}