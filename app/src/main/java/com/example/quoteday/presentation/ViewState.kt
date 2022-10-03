package com.example.quoteday.presentation

data class ViewState(
    val isDownload: Boolean = false,
    val error: Throwable? = null
)