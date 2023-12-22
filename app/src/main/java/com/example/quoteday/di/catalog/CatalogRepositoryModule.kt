package com.example.quoteday.di.catalog

import com.example.catalog.domain.repository.CatalogRepository
import com.example.quoteday.data.repository.CatalogRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
interface CatalogRepositoryModule {
    @Binds
    fun bindCatalogRepository(realisation: CatalogRepositoryImpl): CatalogRepository
}