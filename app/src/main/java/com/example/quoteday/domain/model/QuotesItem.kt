package com.example.quoteday.domain.model

import com.google.gson.annotations.SerializedName

data class QuotesItem(
    @SerializedName("a")
    val author: String,
    @SerializedName("q")
    val quotes: String,
    @SerializedName("c")
    var id: Int,
)