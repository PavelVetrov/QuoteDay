package com.example.quoteday.di

import com.example.quoteday.data.RepositoryQuotesImpl
import com.example.quoteday.domain.RepositoryQuotes
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
interface RepoModule {

    @Binds
    fun bindRepository(repositoryImpl: RepositoryQuotesImpl): RepositoryQuotes
}