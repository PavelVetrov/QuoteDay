package com.example.quoteday.di

import android.app.Application
import com.example.quoteday.data.RepositoryQuotesImpl
import com.example.quoteday.data.database.AppDataBase
import com.example.quoteday.data.database.QuotesItemDao
import com.example.quoteday.domain.RepositoryQuotes
import dagger.Binds
import dagger.Module
import dagger.Provides

@Module
interface DataModule {

    @Binds
    @ApplicationScope
    fun bindRepository(repositoryImpl: RepositoryQuotesImpl): RepositoryQuotes

    companion object{
        @Provides
        @ApplicationScope
        fun providerQuotesItem(application: Application): QuotesItemDao{
            return AppDataBase.getInstance(application).quotesItemDao()
        }
    }
}