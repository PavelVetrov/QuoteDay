package com.example.quoteday.data.database

import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertTrue
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class AppDataBaseTest {

    private lateinit var dataBase: AppDataBase
    private lateinit var dao: QuotesDao

    @Before
    fun setUp() {
        dataBase = Room.inMemoryDatabaseBuilder(
            ApplicationProvider.getApplicationContext(),
            AppDataBase::class.java
        ).allowMainThreadQueries().build()
        dao = dataBase.quotesDao()
    }

    @Test
    fun writeAndReadQuotes() = runBlocking {
        val quotes = QuotesItemEntity(id = 1, quotes = "some quotes", author = "some author")
        dao.addFavoriteQuote(quotes)
        val quotesList = dao.getQuotesList()
        assertTrue(quotesList.first().contains(quotes))
        assertEquals(quotesList.first().size, 1)
    }

    @Test
    fun writeAndDelete() = runBlocking {
        val quotes = QuotesItemEntity(id = 1, quotes = "some quotes", author = "some author")
        dao.addFavoriteQuote(quotes)
        dao.deleteQuoteById(1)
        val quotesList = dao.getQuotesList().first().size
        assertEquals(quotesList, 0)
    }

    @After
    fun closeDb() {
        dataBase.close()
    }

}