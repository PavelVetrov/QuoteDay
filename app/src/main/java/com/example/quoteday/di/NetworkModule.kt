package com.example.quoteday.di

import com.example.quoteday.data.network.QuotesApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

@Module
@InstallIn(SingletonComponent::class)
class NetworkModule {

    @Provides
    fun provideQuotesApi(): QuotesApi {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(QuotesApi::class.java)

    }

    companion object {
        private const val BASE_URL = "https://zenquotes.io/api/"
    }
}