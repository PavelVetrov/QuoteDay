package com.example.quoteday.di

import androidx.lifecycle.ViewModel
import com.example.quoteday.presentation.viewmodel.ViewModalFavoriteFragment
import com.example.quoteday.presentation.viewmodel.ViewModalHomeFragment
import com.example.quoteday.presentation.viewmodel.ViewModalQuotesFragment
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
interface ViewModalModule {
    @Binds
    @IntoMap
    @ViewModalKey(ViewModalFavoriteFragment::class)
    fun bindViewModalFavoriteFragment(viewModal: ViewModalFavoriteFragment): ViewModel

    @Binds
    @IntoMap
    @ViewModalKey(ViewModalHomeFragment::class)
    fun bindViewModalHomeFragment(viewModal: ViewModalHomeFragment): ViewModel

    @Binds
    @IntoMap
    @ViewModalKey(ViewModalQuotesFragment::class)
    fun bindViewModalQuotesFragment(viewModal: ViewModalQuotesFragment): ViewModel

}