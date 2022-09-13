package com.example.quoteday.di

import android.content.Context
import androidx.room.Room
import com.example.quoteday.data.database.AppDataBase
import com.example.quoteday.data.database.QuotesItemDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class DataBaseModule {

    @Provides
    @Singleton
    fun provideDataBase(@ApplicationContext context: Context): AppDataBase {
        return Room.databaseBuilder(
            context,
            AppDataBase::class.java,
            DB_NAME
        ).build()
    }

    @Provides
    @Singleton
    fun provideDao(dataBase: AppDataBase): QuotesItemDao {
        return dataBase.quotesItemDao()
    }

    companion object {
        private const val DB_NAME = "quotes_Db_name"
    }
}