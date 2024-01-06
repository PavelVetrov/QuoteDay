package com.example.quoteday.data.mappers

import com.example.home.domain.entity.QuoteModelHome
import com.example.quoteday.data.database.QuotesItemEntity
import com.example.quoteday.domain.model.QuoteModel
import org.junit.Assert.*
import org.junit.Before

import org.junit.Test

class QuotesMapperHomeTest {

    private lateinit var quotesMapperHome: QuotesMapperHome

    @Before
    fun before() {
        quotesMapperHome = QuotesMapperHome()
    }

    @Test
    fun mapEntityToDbModal() {
        val quoteModelHome = QuoteModelHome(
            id = 0,
            author = "some author",
            quote = "some quote"
        )
        val mapEntityToDbModalValue = quotesMapperHome.mapEntityToDbModal(quoteModelHome)
        val quotesItemEntity = QuotesItemEntity(
            id = 0,
            author = "some author",
            quotes = "some quote"
        )

        assertEquals(mapEntityToDbModalValue, quotesItemEntity)
    }

    @Test
    fun mapQuotesToQuotesHome() {
        val quoteModel = QuoteModel(
            id = 0,
            author = "some author",
            quote = "some quote"
        )
        val mapQuotesToQuotesHomeValue = quotesMapperHome.mapQuotesToQuotesHome(quoteModel)
        val quoteModelHome = QuoteModelHome(
            id = 0,
            author = "some author",
            quote = "some quote"
        )
        assertEquals(mapQuotesToQuotesHomeValue, quoteModelHome)
    }
}