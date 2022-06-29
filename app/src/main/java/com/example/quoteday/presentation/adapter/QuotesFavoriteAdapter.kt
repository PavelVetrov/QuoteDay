package com.example.quoteday.presentation.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import com.example.quoteday.databinding.QuotesFavoriteItemBinding
import com.example.quoteday.domain.model.QuotesItem

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
        holder.binding.quotesFavoriteText.text = getQuote.q
        holder.binding.quoteFavoriteAuthor.text = getQuote.a
        holder.binding.bottomDelete.setOnClickListener {

            onClickListenerDeleteQuote?.onClickDeleteQuote(getQuote)
        }

    }

    interface OnClickListenerDeleteQuote {

        fun onClickDeleteQuote(quotesItem: QuotesItem)
    }
}