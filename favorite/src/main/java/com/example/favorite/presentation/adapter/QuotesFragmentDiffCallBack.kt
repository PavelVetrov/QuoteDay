package com.example.favorite.presentation.adapter

import androidx.recyclerview.widget.DiffUtil
import com.example.favorite.domain.entity.QuoteModalFavorite

class QuotesFragmentDiffCallBack : DiffUtil.ItemCallback<QuoteModalFavorite>() {

    override fun areItemsTheSame(oldItem: QuoteModalFavorite, newItem: QuoteModalFavorite): Boolean {
        return oldItem.id == newItem.id
    }
    override fun areContentsTheSame(oldItem: QuoteModalFavorite, newItem: QuoteModalFavorite): Boolean {
        return oldItem == newItem
    }
}