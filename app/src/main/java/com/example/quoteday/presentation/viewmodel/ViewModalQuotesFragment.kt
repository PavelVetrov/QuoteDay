package com.example.quoteday.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.quoteday.domain.AddFavoriteQuoteUseCase
import com.example.quoteday.domain.GetQuotesListDtoUseCase
import com.example.quoteday.domain.model.Quotes
import com.example.quoteday.domain.model.QuotesItem
import kotlinx.coroutines.launch
import retrofit2.Response
import javax.inject.Inject

class ViewModalQuotesFragment @Inject constructor(
    private val addFavoriteQuoteUseCase: AddFavoriteQuoteUseCase,
    private val getQuotesListDto: GetQuotesListDtoUseCase
) : ViewModel() {


    private val _getQuotesList = MutableLiveData<Response<Quotes>>()
    val getQuotesList: LiveData<Response<Quotes>> = _getQuotesList

    fun getQuotesList() {

        viewModelScope.launch {

            try {
                val quotesList = getQuotesListDto.invoke()
                _getQuotesList.value = quotesList

            } catch (e: Exception) {

            }
        }
    }

    fun addFavoriteQuote(quotesItem: QuotesItem) {
        viewModelScope.launch {
            addFavoriteQuoteUseCase.invoke(quotesItem)
        }
    }
}