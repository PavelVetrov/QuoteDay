package com.example.home.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.home.domain.usecase.AddFavoriteQuoteUseCase
import com.example.home.domain.usecase.GetQuoteDayUseCase
import com.example.home.domain.entity.QuoteModelHome
import com.example.home.domain.usecase.DeleteHomeFavoriteQuoteUseCase
import com.example.home.domain.usecase.GetHomeFavoriteQuotesUseCase

import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeQuoteViewModal @Inject constructor(
    private val getQuoteDayUseCase: GetQuoteDayUseCase,
    private val addFavoriteQuoteUseCase: AddFavoriteQuoteUseCase,
    private val deleteHomeFavoriteQuoteUseCase: DeleteHomeFavoriteQuoteUseCase,
    private val getHomeFavoriteQuotesUseCase: GetHomeFavoriteQuotesUseCase
) : ViewModel() {

    private val errorHandler = CoroutineExceptionHandler { _, error ->
        _viewStateHome.value = ViewStateHome(error = error)
    }
    private val _viewStateHome = MutableLiveData<ViewStateHome>()
    val viewState: LiveData<ViewStateHome> = _viewStateHome

    private val _responseQuoteDay =
        MutableStateFlow<QuoteModelHome?>(null)
    val responseQuoteDay: StateFlow<QuoteModelHome?> = _responseQuoteDay.asStateFlow()

    private val _favoriteQuoteDay = MutableStateFlow(false)
    val favoriteQuoteDay: StateFlow<Boolean> = _favoriteQuoteDay.asStateFlow()

    fun addFavoriteQuote(quoteModel: QuoteModelHome) {
        viewModelScope.launch {
            val favoriteQuote = getFavoriteQuotes(quoteModel)
            if (favoriteQuote == null) {
                addFavoriteQuoteUseCase.invoke(quoteModel)
            } else {
                deleteHomeFavoriteQuoteUseCase.invoke(quoteModel)
            }
        }
    }

    suspend fun getFavoriteQuotes(quoteModel: QuoteModelHome): QuoteModelHome? {
        val favoriteListQuote = getHomeFavoriteQuotesUseCase.invoke().firstOrNull()
        val favoriteQuote = favoriteListQuote?.firstOrNull { it.quote == quoteModel.quote }
        _favoriteQuoteDay.value = favoriteQuote != null
        return favoriteQuote
    }

    fun getQuoteDay() {
        viewModelScope.launch(errorHandler) {
            val response = getQuoteDayUseCase.invoke()
            _responseQuoteDay.value = response
            _viewStateHome.value = ViewStateHome(true)
        }
    }
}

