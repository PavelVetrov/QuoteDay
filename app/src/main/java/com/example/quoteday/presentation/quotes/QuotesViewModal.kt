package com.example.quoteday.presentation.quotes

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.quoteday.domain.model.Quotes
import com.example.quoteday.domain.model.QuoteModel
import com.example.quoteday.domain.usecases.AddFavoriteQuoteUseCase
import com.example.quoteday.domain.usecases.GetQuotesListDtoUseCase
import com.example.quoteday.presentation.ViewState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class QuotesViewModal @Inject constructor(
    private val addFavoriteQuoteUseCase: AddFavoriteQuoteUseCase,
    private val getQuotesListDto: GetQuotesListDtoUseCase
) : ViewModel() {

    private val errorHandler = CoroutineExceptionHandler { _, error ->
        _viewStateQuotes.value = ViewState(error = error)
    }
    private val _viewStateQuotes = MutableLiveData<ViewState>()
    val viewStateQuotes: LiveData<ViewState> = _viewStateQuotes

    private val _getQuotes = MutableStateFlow<Quotes?>(null)
    val getQuotes: StateFlow<Quotes?> = _getQuotes.asStateFlow()

    fun getQuotesList() {
        viewModelScope.launch(errorHandler) {
            val quotesList = getQuotesListDto.invoke()
            if (quotesList.isSuccessful) {
                quotesList.body()?.let {
                    _getQuotes.value = it
                    _viewStateQuotes.value = ViewState(true)
                }
            }
        }
    }
    fun addFavoriteQuote(quoteModel: QuoteModel) {
        viewModelScope.launch {
            addFavoriteQuoteUseCase.invoke(quoteModel)
        }
    }
}