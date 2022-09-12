package com.example.quoteday.presentation.quotesfragment.adapter

import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import com.example.quoteday.databinding.QuotesItemBinding
import com.example.quoteday.domain.model.QuotesItem
import com.example.quoteday.presentation.utils.QuotesFragmentDiffCallBack

class QuotesFragmentAdapter :
    ListAdapter<QuotesItem, QuoteFragmentViewHolder>(QuotesFragmentDiffCallBack()) {

    var onClickListenerSaveFavorite: OnClickListenerSaveFavorite? = null
    var onClickListenerShareQuote: OnClickListenerShareQuote? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): QuoteFragmentViewHolder {
        val binding = QuotesItemBinding.inflate(
            LayoutInflater.from(
                parent.context
            ), parent, false
        )
        return QuoteFragmentViewHolder(binding)
    }
    override fun onBindViewHolder(holder: QuoteFragmentViewHolder, position: Int) {
        val quotes = getItem(position)
        holder.binding.quotesText.text = quotes.quotes
        holder.binding.quoteAuthor.text = quotes.author
        holder.binding.buttonSave.setOnClickListener {
            holder.binding.buttonSave.animate().apply {
                duration = 1000
                rotationY(360f)
            }.start()

            holder.binding.buttonSave.setColorFilter(Color.RED)
            onClickListenerSaveFavorite?.onClickSaveFavorite(quotes)
        }
        holder.binding.shareButton.setOnClickListener {
            onClickListenerShareQuote?.onClickShare(quotes)
        }
    }
    interface OnClickListenerSaveFavorite {
        fun onClickSaveFavorite(quotesItem: QuotesItem)
    }

    interface OnClickListenerShareQuote {
        fun onClickShare(quotesItem: QuotesItem)
    }
}

