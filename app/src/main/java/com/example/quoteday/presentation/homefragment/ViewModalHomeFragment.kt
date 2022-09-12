package com.example.quoteday.presentation.homefragment

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.quoteday.domain.model.Quotes
import com.example.quoteday.domain.model.QuotesItem
import com.example.quoteday.domain.usecases.AddFavoriteQuoteUseCase
import com.example.quoteday.domain.usecases.GetQuoteDayDtoUseCase
import com.example.quoteday.presentation.ViewState
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch
import retrofit2.Response
import javax.inject.Inject

class ViewModalHomeFragment @Inject constructor(
    private val getQuoteDayDtoUseCase: GetQuoteDayDtoUseCase,
    private val addFavoriteQuoteUseCase: AddFavoriteQuoteUseCase
) : ViewModel() {

    private val errorHandler = CoroutineExceptionHandler { _, error ->
        _viewStateHome.value = ViewState(e = error)
    }
    private val _viewStateHome = MutableLiveData<ViewState>()
    val viewState: LiveData<ViewState> = _viewStateHome

    private val _responseQuoteDay = MutableLiveData<QuotesItem>()
    val responseQuoteDay: LiveData<QuotesItem> = _responseQuoteDay

    fun addFavoriteQuote(quotesItem: QuotesItem) {
        viewModelScope.launch {
            addFavoriteQuoteUseCase.invoke(quotesItem)
        }
    }
    fun getQuoteDay() {
        viewModelScope.launch(errorHandler) {
            val response = getQuoteDayDtoUseCase.invoke()
            if (response.isSuccessful){
                response.body()?.let {
                    val getQuotesItem = it[0]
                    _responseQuoteDay.value = getQuotesItem
                    _viewStateHome.value = ViewState(true)
                }
            }

        }
    }
}

