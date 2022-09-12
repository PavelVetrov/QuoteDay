package com.example.quoteday.presentation

import android.app.Application
import com.example.quoteday.di.DaggerApplicationComponent

class QuotesApplication: Application() {

    val component by lazy {
        DaggerApplicationComponent.factory().create(this)
    }
    override fun onCreate() {
        component.inject(this)
        super.onCreate()
    }
}