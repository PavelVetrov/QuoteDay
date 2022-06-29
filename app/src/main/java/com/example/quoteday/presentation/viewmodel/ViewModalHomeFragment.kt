package com.example.quoteday.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.quoteday.domain.AddFavoriteQuoteUseCase
import com.example.quoteday.domain.GetQuoteDayDtoUseCase
import com.example.quoteday.domain.model.Quotes
import com.example.quoteday.domain.model.QuotesItem
import kotlinx.coroutines.launch
import retrofit2.Response
import javax.inject.Inject

class ViewModalHomeFragment @Inject constructor(
    private val getQuoteDayDtoUseCase: GetQuoteDayDtoUseCase,
    private val addFavoriteQuoteUseCase: AddFavoriteQuoteUseCase
) : ViewModel() {


    private val _responseQuoteDay = MutableLiveData<Response<Quotes>>()
    val responseQuoteDay: LiveData<Response<Quotes>> = _responseQuoteDay

    fun addFavoriteQuote(quotesItem: QuotesItem) {
        viewModelScope.launch {
            addFavoriteQuoteUseCase.invoke(quotesItem)
        }
    }

    fun getQuoteDay() {
        viewModelScope.launch {

            try {
                val response = getQuoteDayDtoUseCase.invoke()
                _responseQuoteDay.value = response
            } catch (e: Exception) {

            }

        }
    }

}

