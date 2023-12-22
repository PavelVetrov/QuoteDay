package com.example.catalog.presentation

data class ViewStateCatalog(
    val isDownload: Boolean = false,
    val error: Throwable? = null
)
