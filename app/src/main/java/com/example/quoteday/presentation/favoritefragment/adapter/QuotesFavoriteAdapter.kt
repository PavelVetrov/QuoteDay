package com.example.quoteday.presentation.favoritefragment.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import com.example.quoteday.databinding.QuotesFavoriteItemBinding
import com.example.quoteday.domain.model.QuotesItem
import com.example.quoteday.presentation.utils.QuotesFragmentDiffCallBack

class QuotesFavoriteAdapter :
    ListAdapter<QuotesItem, QuotesFavoriteViewHolder>(QuotesFragmentDiffCallBack()) {

    var onClickListenerDeleteQuote: OnClickListenerDeleteQuote? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): QuotesFavoriteViewHolder {
        val binding =
            QuotesFavoriteItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return QuotesFavoriteViewHolder(binding)
    }

    override fun onBindViewHolder(holder: QuotesFavoriteViewHolder, position: Int) {
        val getQuote = getItem(position)
        with(holder.binding) {
            quotesFavoriteText.text = getQuote.quotes
            quoteFavoriteAuthor.text = getQuote.author
            bottomDelete.setOnClickListener {
                onClickListenerDeleteQuote?.onClickDeleteQuote(getQuote)
            }
        }
    }

    interface OnClickListenerDeleteQuote {

        fun onClickDeleteQuote(quotesItem: QuotesItem)
    }
}