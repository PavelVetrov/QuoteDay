package com.example.quoteday.presentation.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.quoteday.domain.model.QuoteModel
import com.example.quoteday.domain.usecases.AddFavoriteQuoteUseCase
import com.example.quoteday.domain.usecases.GetQuoteDayUseCase
import com.example.quoteday.presentation.ViewState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeQuoteViewModal @Inject constructor(
    private val getQuoteDayUseCase: GetQuoteDayUseCase,
    private val addFavoriteQuoteUseCase: AddFavoriteQuoteUseCase
) : ViewModel() {

    private val errorHandler = CoroutineExceptionHandler { _, error ->
        _viewStateHome.value = ViewState(error = error)
    }
    private val _viewStateHome = MutableLiveData<ViewState>()
    val viewState: LiveData<ViewState> = _viewStateHome

    private val _responseQuoteDay = MutableLiveData<QuoteModel>()
    val responseQuoteDay: LiveData<QuoteModel> = _responseQuoteDay

    fun addFavoriteQuote(quoteModel: QuoteModel) {
        viewModelScope.launch {
            addFavoriteQuoteUseCase.invoke(quoteModel)
        }
    }

    fun getQuoteDay() {
        viewModelScope.launch(errorHandler) {
            val response = getQuoteDayUseCase.invoke()
            if (response.isSuccessful) {
                response.body()?.let {
                    val getQuotesItem = it[0]
                    _responseQuoteDay.value = getQuotesItem
                    _viewStateHome.value = ViewState(true)
                }
            }

        }
    }
}

