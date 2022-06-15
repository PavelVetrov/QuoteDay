package com.example.quoteday.presentation.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.quoteday.data.RepositoryQuotesImpl
import com.example.quoteday.domain.DeleteFavoriteQuoteUseCase
import com.example.quoteday.domain.GetFavoriteQuotesDbUseCase
import com.example.quoteday.domain.model.QuotesItem
import kotlinx.coroutines.launch

class ViewModalFavoriteFragment(application: Application): AndroidViewModel(application) {

    private val repositoryQuotesImpl = RepositoryQuotesImpl(application)

   private val favoriteQuotesDbUseCase = GetFavoriteQuotesDbUseCase(repositoryQuotesImpl)

    private val deleteFavoriteQuoteUseCase = DeleteFavoriteQuoteUseCase(repositoryQuotesImpl)

    val favoriteQuotes = favoriteQuotesDbUseCase.invoke()

    fun deleteQuotes(quotesItem: QuotesItem){
        viewModelScope.launch{
            deleteFavoriteQuoteUseCase.invoke(quotesItem)
        }
    }
}