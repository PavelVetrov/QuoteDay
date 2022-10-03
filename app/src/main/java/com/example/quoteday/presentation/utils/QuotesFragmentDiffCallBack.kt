package com.example.quoteday.presentation.utils

import androidx.recyclerview.widget.DiffUtil
import com.example.quoteday.domain.model.QuoteModel

class QuotesFragmentDiffCallBack : DiffUtil.ItemCallback<QuoteModel>() {

    override fun areItemsTheSame(oldItem: QuoteModel, newItem: QuoteModel): Boolean {
        return oldItem.id == newItem.id
    }
    override fun areContentsTheSame(oldItem: QuoteModel, newItem: QuoteModel): Boolean {
        return oldItem == newItem
    }
}