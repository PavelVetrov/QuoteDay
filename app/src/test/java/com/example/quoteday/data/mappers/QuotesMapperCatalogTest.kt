package com.example.quoteday.data.mappers


import com.example.catalog.domain.entity.QuoteModelCatalog
import com.example.catalog.domain.entity.QuotesCatalog
import com.example.quoteday.data.database.QuotesItemEntity
import com.example.quoteday.domain.model.QuoteModel
import org.junit.Test
import org.junit.Assert.assertEquals

class QuotesMapperCatalogTest {

    @Test
    fun toMapEntityToDbModal() {
        val quotesMapper = QuotesMapperCatalog()
        val quoteModelCatalog = QuoteModelCatalog(
            author = "some author",
            quote = "some quote",
            id = 0
        )
        val entityMapper = quotesMapper.mapEntityToDbModal(quoteModelCatalog)
        val quotesItemEntity = QuotesItemEntity(
            id = 0,
            author = "some author",
            quotes = "some quote"
        )

        assertEquals(entityMapper, quotesItemEntity)
    }

    @Test
    fun toMapQuotesToQuotesCatalog() {
        val quotesMapper = QuotesMapperCatalog()
        val quoteModel = QuoteModel(
            id = 0,
            author = "some author",
            quote = "some quote"
        )
        val quotesModelList = arrayListOf(quoteModel)
        val entityMapper = quotesMapper.mapQuotesToQuotesCatalog(quotesModelList)
        val quotesCatalogModel = QuoteModelCatalog(
            id = 0,
            author = "some author",
            quote = "some quote"
        )
        val quotesCatalogList = arrayListOf(quotesCatalogModel)
        val quotesCatalog = QuotesCatalog(quotesCatalogList)

        assertEquals(entityMapper, quotesCatalog)
    }
}