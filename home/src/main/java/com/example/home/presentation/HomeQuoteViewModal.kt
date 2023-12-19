package com.example.home.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.home.domain.usecase.AddFavoriteQuoteUseCase
import com.example.home.domain.usecase.GetQuoteDayUseCase
import com.example.home.domain.entity.QuoteModelHome

import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeQuoteViewModal @Inject constructor(
    private val getQuoteDayUseCase: GetQuoteDayUseCase,
    private val addFavoriteQuoteUseCase: AddFavoriteQuoteUseCase
) : ViewModel() {

    private val errorHandler = CoroutineExceptionHandler { _, error ->
        _viewStateHome.value = ViewStateHome(error = error)
    }
    private val _viewStateHome = MutableLiveData<ViewStateHome>()
    val viewState: LiveData<ViewStateHome> = _viewStateHome

    private val _responseQuoteDay =
        MutableStateFlow<QuoteModelHome?>(null)
    val responseQuoteDay: StateFlow<QuoteModelHome?> = _responseQuoteDay.asStateFlow()

    fun addFavoriteQuote(quoteModel: QuoteModelHome) {
        viewModelScope.launch {
            addFavoriteQuoteUseCase.invoke(quoteModel)
        }
    }

    fun getQuoteDay() {
        viewModelScope.launch(errorHandler) {
            val response = getQuoteDayUseCase.invoke()
            _responseQuoteDay.value = response
            _viewStateHome.value = ViewStateHome(true)
        }
    }
}

