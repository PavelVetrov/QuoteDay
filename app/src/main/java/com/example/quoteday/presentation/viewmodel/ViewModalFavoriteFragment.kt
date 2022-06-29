package com.example.quoteday.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.quoteday.domain.DeleteFavoriteQuoteUseCase
import com.example.quoteday.domain.GetFavoriteQuotesDbUseCase
import com.example.quoteday.domain.model.QuotesItem
import kotlinx.coroutines.launch
import javax.inject.Inject

class ViewModalFavoriteFragment @Inject constructor(
    private val favoriteQuotesDbUseCase: GetFavoriteQuotesDbUseCase,
    private val deleteFavoriteQuoteUseCase: DeleteFavoriteQuoteUseCase
) : ViewModel() {


    val favoriteQuotes = favoriteQuotesDbUseCase.invoke()

    fun deleteQuotes(quotesItem: QuotesItem) {
        viewModelScope.launch {
            deleteFavoriteQuoteUseCase.invoke(quotesItem)
        }
    }
}