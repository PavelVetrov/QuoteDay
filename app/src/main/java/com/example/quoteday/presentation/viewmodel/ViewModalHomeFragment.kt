package com.example.quoteday.presentation.viewmodel

import android.app.Application
import androidx.lifecycle.*
import com.example.quoteday.data.RepositoryQuotesImpl
import com.example.quoteday.domain.AddFavoriteQuoteUseCase
import com.example.quoteday.domain.GetQuoteDayDtoUseCase
import com.example.quoteday.domain.model.Quotes
import com.example.quoteday.domain.model.QuotesItem
import kotlinx.coroutines.launch
import retrofit2.HttpException
import retrofit2.Response

class ViewModalHomeFragment (application: Application): AndroidViewModel(application) {

    private val repositoryQuotesImpl = RepositoryQuotesImpl(application)

    private val getQuoteDayDtoUseCase = GetQuoteDayDtoUseCase(repositoryQuotesImpl)

    private val addFavoriteQuoteUseCase = AddFavoriteQuoteUseCase(repositoryQuotesImpl)

    private val _responseQuoteDay = MutableLiveData<Response<Quotes>>()
    val responseQuoteDay: LiveData<Response<Quotes>> = _responseQuoteDay


    fun addFavoriteQuote(quotesItem: QuotesItem){
        viewModelScope.launch {
            addFavoriteQuoteUseCase.invoke(quotesItem)
        }
    }


    fun getQuoteDay() {
        viewModelScope.launch {
            try {
                val response = getQuoteDayDtoUseCase.invoke()
                _responseQuoteDay.value = response
            }catch (e: HttpException){

            }



        }
    }

}

