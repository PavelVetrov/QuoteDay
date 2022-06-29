package com.example.quoteday.di

import android.app.Application
import com.example.quoteday.presentation.FavoriteFragment
import com.example.quoteday.presentation.HomeFragment
import com.example.quoteday.presentation.QuotesApplication
import com.example.quoteday.presentation.QuotesFragment
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