package com.example.quoteday.di

import android.app.Application
import com.example.quoteday.presentation.favoritefragment.FavoriteFragment
import com.example.quoteday.presentation.homefragment.HomeFragment
import com.example.quoteday.presentation.QuotesApplication
import com.example.quoteday.presentation.quotesfragment.QuotesFragment
import dagger.BindsInstance
import dagger.Component

@ApplicationScope
@Component(modules = [DataModule::class, ViewModalModule::class])
interface ApplicationComponent {

    fun inject(fragment: FavoriteFragment)

    fun inject(fragment: HomeFragment)

    fun inject(fragment: QuotesFragment)

    fun inject(application: QuotesApplication)

    @Component.Factory
    interface Factory{
        fun create(@BindsInstance
        application: Application): ApplicationComponent
    }
}