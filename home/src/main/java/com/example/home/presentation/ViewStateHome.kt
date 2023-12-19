package com.example.home.presentation

data class ViewStateHome(
    val isDownload: Boolean = false,
    val error: Throwable? = null
)
