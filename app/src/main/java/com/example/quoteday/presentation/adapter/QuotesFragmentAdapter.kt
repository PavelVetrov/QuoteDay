package com.example.quoteday.presentation.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import com.example.quoteday.databinding.FragmentQuotesBinding
import com.example.quoteday.databinding.QuotesItemBinding
import com.example.quoteday.domain.model.QuotesItem

class QuotesFragmentAdapter :
    ListAdapter<QuotesItem, QuoteFragmentViewHolder>(QuotesFragmentDiffCallBack()) {

    var onClickListenerSaveFavorite: OnClickListenerSaveFavorite? = null

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
        holder.binding.quotesText.text = quotes.q
        holder.binding.quoteAuthor.text = quotes.a
        holder.binding.bottomSave.setOnClickListener {

            onClickListenerSaveFavorite?.onClickSaveFavorite(quotes)

            true
        }

    }
    interface OnClickListenerSaveFavorite{

        fun onClickSaveFavorite(quotesItem: QuotesItem)
    }

    companion object{
        const val VIEW_TYPE_ENABLED = 0
        const val VIEW_TYPE_DISABLED = 1
    }
}

