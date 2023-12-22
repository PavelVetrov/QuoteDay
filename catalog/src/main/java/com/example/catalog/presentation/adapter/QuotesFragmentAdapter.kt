package com.example.catalog.presentation.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import com.example.catalog.R
import com.example.catalog.databinding.QuotesItemBinding
import com.example.catalog.domain.entity.QuoteModelCatalog

class QuotesFragmentAdapter(
    private val onClickListenerQuotes: OnClickListenerQuotes
) :
    ListAdapter<QuoteModelCatalog, QuoteFragmentViewHolder>(QuotesCatalogFragmentDiffCallBack()),
    View.OnClickListener {

    override fun onClick(v: View) {
        val quoteModel = v.tag as QuoteModelCatalog
        when (v.id) {
            R.id.radioFavoriteButton -> onClickListenerQuotes.onClickSaveFavorite(quoteModel)
            R.id.share_button_quotes -> onClickListenerQuotes.onClickShare(quoteModel)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): QuoteFragmentViewHolder {
        val binding = QuotesItemBinding.inflate(
            LayoutInflater.from(
                parent.context
            ), parent, false
        )
        binding.radioFavoriteButton.setOnClickListener(this).apply {
            !binding.radioFavoriteButton.isChecked
        }
        binding.shareButtonQuotes.setOnClickListener(this)
        return QuoteFragmentViewHolder(binding)
    }

    override fun onBindViewHolder(holder: QuoteFragmentViewHolder, position: Int) {
        val quotes = getItem(position)
        with(holder.binding) {
            radioFavoriteButton.tag = quotes
            shareButtonQuotes.tag = quotes

            quotesText.text = quotes.quote
            quoteAuthor.text = quotes.author
        }
    }

    interface OnClickListenerQuotes {
        fun onClickSaveFavorite(quoteModel: QuoteModelCatalog)
        fun onClickShare(quoteModel: QuoteModelCatalog)
    }

}

