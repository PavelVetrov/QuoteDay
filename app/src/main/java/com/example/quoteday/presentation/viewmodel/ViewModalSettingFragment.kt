package com.example.quoteday.presentation.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.quoteday.data.RepositoryQuotesImpl
import com.example.quoteday.domain.GetQuoteDayDtoUseCase
import com.example.quoteday.domain.model.Quotes
import kotlinx.coroutines.launch
import retrofit2.HttpException
import retrofit2.Response

class ViewModalSettingFragment(application: Application): AndroidViewModel(application) {

    private val repositoryQuotesImpl = RepositoryQuotesImpl(application)

    private val getQuoteDayDtoUseCase = GetQuoteDayDtoUseCase(repositoryQuotesImpl)

    private val _responseQuoteDay = MutableLiveData<Response<Quotes>>()
    val responseQuoteDay: LiveData<Response<Quotes>> = _responseQuoteDay


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