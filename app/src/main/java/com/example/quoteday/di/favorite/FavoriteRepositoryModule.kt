package com.example.quoteday.di.favorite

import com.example.favorite.domain.repository.FavoriteRepository
import com.example.quoteday.data.repository.FavoriteRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
interface FavoriteRepositoryModule {
    @Binds
    fun bindFavoriteRepository(realisation: FavoriteRepositoryImpl): FavoriteRepository
}