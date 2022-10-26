package com.example.quoteday.presentation.favorite

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.quoteday.domain.model.QuoteModel
import com.example.quoteday.domain.usecases.DeleteFavoriteQuoteUseCase
import com.example.quoteday.domain.usecases.GetFavoriteQuotesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FavoriteQuotesViewModal @Inject constructor(
    favoriteQuotesDbUseCase: GetFavoriteQuotesUseCase,
    private val deleteFavoriteQuoteUseCase: DeleteFavoriteQuoteUseCase
) : ViewModel() {

    val favoriteQuotes = favoriteQuotesDbUseCase()

    fun deleteQuotes(quoteModel: QuoteModel) {
        viewModelScope.launch {
            deleteFavoriteQuoteUseCase.invoke(quoteModel)
        }
    }
}