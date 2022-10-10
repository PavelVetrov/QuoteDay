package com.example.quoteday.presentation.quotes.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import com.example.quoteday.R
import com.example.quoteday.databinding.QuotesItemBinding
import com.example.quoteday.domain.model.QuoteModel
import com.example.quoteday.presentation.utils.QuotesFragmentDiffCallBack

class QuotesFragmentAdapter(
    private val onClickListenerQuotes: OnClickListenerQuotes
) :
    ListAdapter<QuoteModel, QuoteFragmentViewHolder>(QuotesFragmentDiffCallBack()),
    View.OnClickListener {

    override fun onClick(v: View) {
        val quoteModel = v.tag as QuoteModel
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
        fun onClickSaveFavorite(quoteModel: QuoteModel)
        fun onClickShare(quoteModel: QuoteModel)
    }


}

