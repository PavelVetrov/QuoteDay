package com.example.quoteday.domain.model

import com.google.gson.annotations.SerializedName

data class QuoteModel(
    @SerializedName("a")
    val author: String,
    @SerializedName("q")
    val quote: String,
    @SerializedName("c")
    val id: Int,
)