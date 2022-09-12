package com.example.quoteday.presentation.favoritefragment

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.quoteday.domain.usecases.DeleteFavoriteQuoteUseCase
import com.example.quoteday.domain.usecases.GetFavoriteQuotesDbUseCase
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