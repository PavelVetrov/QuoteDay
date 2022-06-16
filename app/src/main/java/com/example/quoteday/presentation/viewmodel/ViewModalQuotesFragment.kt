package com.example.quoteday.presentation.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.quoteday.data.RepositoryQuotesImpl
import com.example.quoteday.domain.AddFavoriteQuoteUseCase
import com.example.quoteday.domain.GetQuotesListDtoUseCase
import com.example.quoteday.domain.model.Quotes
import com.example.quoteday.domain.model.QuotesItem
import kotlinx.coroutines.launch
import retrofit2.HttpException
import retrofit2.Response

class ViewModalQuotesFragment(application: Application) : AndroidViewModel(application) {

    private val repositoryQuotesImpl = RepositoryQuotesImpl(application)

    private val addFavoriteQuoteUseCase = AddFavoriteQuoteUseCase(repositoryQuotesImpl)

    private val getQuotesListDto = GetQuotesListDtoUseCase(repositoryQuotesImpl)

    private val _getQuotesList = MutableLiveData<Response<Quotes>>()
    val getQuotesList: LiveData<Response<Quotes>> = _getQuotesList

    fun getQuotesList() {

        try {
            viewModelScope.launch {
                val quotesList = getQuotesListDto.invoke()
                _getQuotesList.value = quotesList
            }

        } catch (e: HttpException) {

        }
    }

    fun addFavoriteQuote(quotesItem: QuotesItem) {
        viewModelScope.launch {
            addFavoriteQuoteUseCase.invoke(quotesItem)
        }
    }

}