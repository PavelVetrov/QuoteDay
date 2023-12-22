package com.example.catalog.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.catalog.domain.entity.QuoteModelCatalog
import com.example.catalog.domain.entity.QuotesCatalog
import com.example.catalog.domain.usecase.AddFavoriteQuoteUseCase
import com.example.catalog.domain.usecase.GetQuotesListDtoUseCase
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
        _viewStateQuotes.value = ViewStateCatalog(error = error)
    }
    private val _viewStateQuotes = MutableLiveData<ViewStateCatalog>()
    val viewStateQuotes: LiveData<ViewStateCatalog> = _viewStateQuotes

    private val _getQuotes = MutableStateFlow<QuotesCatalog?>(null)
    val getQuotes: StateFlow<QuotesCatalog?> = _getQuotes.asStateFlow()

    fun getQuotesList() {
        viewModelScope.launch(errorHandler) {
            val quotesList = getQuotesListDto.invoke()
            _getQuotes.value = quotesList
            _viewStateQuotes.value = ViewStateCatalog(true)
        }
    }
    fun addFavoriteQuote(quoteModel: QuoteModelCatalog) {
        viewModelScope.launch {
            addFavoriteQuoteUseCase.invoke(quoteModel)
        }
    }
}