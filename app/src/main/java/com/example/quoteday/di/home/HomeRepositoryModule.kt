package com.example.quoteday.di.home

import com.example.home.domain.repository.HomeRepository
import com.example.quoteday.data.repository.HomeRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
@Module
@InstallIn(SingletonComponent::class)
interface HomeRepositoryModule {
    @Binds
    fun bindHomeRepository(realisation: HomeRepositoryImpl): HomeRepository

}