package com.example.quoteday.presentation

data class ViewState(
    val isDownload: Boolean = false,
    val e: Throwable? = null
)