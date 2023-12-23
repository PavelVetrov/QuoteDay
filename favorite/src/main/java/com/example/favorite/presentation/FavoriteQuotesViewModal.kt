package com.example.favorite.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.favorite.domain.entity.QuoteModalFavorite
import com.example.favorite.domain.usecase.DeleteFavoriteQuoteUseCase
import com.example.favorite.domain.usecase.GetFavoriteQuotesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FavoriteQuotesViewModal @Inject constructor(
    favoriteQuotesDbUseCase: GetFavoriteQuotesUseCase,
    private val deleteFavoriteQuoteUseCase: DeleteFavoriteQuoteUseCase
) : ViewModel() {

    val favoriteQuotes = favoriteQuotesDbUseCase()

    fun deleteQuotes(quoteModel: QuoteModalFavorite) {
        viewModelScope.launch {
            deleteFavoriteQuoteUseCase.invoke(quoteModel)
        }
    }
}