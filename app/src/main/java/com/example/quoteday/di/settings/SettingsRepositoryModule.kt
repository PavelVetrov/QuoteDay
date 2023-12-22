package com.example.quoteday.di.settings

import com.example.quoteday.data.repository.SettingsRepositoryImpl
import com.example.settings.domain.repository.SettingsRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
@Module
@InstallIn(SingletonComponent::class)
interface SettingsRepositoryModule {

    @Binds
    fun bindSettingsRepository(realisation: SettingsRepositoryImpl): SettingsRepository

}