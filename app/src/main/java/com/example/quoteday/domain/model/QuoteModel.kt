package com.example.quoteday.domain.model

import com.example.core.extention.Constants.DEFAULT_VALUE
import com.example.core.extention.Constants.EMPTY_STRING
import com.google.gson.annotations.SerializedName

data class QuoteModel(
    @SerializedName("a")
    val author: String = EMPTY_STRING,
    @SerializedName("q")
    val quote: String = EMPTY_STRING,
    @SerializedName("c")
    val id: Int = DEFAULT_VALUE,
)