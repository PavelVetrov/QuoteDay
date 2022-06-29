package com.example.quoteday.presentation.adapter

import androidx.recyclerview.widget.DiffUtil
import com.example.quoteday.domain.model.QuotesItem

class QuotesFragmentDiffCallBack : DiffUtil.ItemCallback<QuotesItem>() {

    override fun areItemsTheSame(oldItem: QuotesItem, newItem: QuotesItem): Boolean {
        return oldItem.c == newItem.c
    }

    override fun areContentsTheSame(oldItem: QuotesItem, newItem: QuotesItem): Boolean {
        return oldItem == newItem
    }
}