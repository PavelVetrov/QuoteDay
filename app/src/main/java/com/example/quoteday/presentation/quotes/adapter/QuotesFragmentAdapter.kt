package com.example.quoteday.presentation.quotes.adapter

import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import com.example.quoteday.databinding.QuotesItemBinding
import com.example.quoteday.domain.model.QuoteModel
import com.example.quoteday.presentation.utils.QuotesFragmentDiffCallBack

class QuotesFragmentAdapter :
    ListAdapter<QuoteModel, QuoteFragmentViewHolder>(QuotesFragmentDiffCallBack()){

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
        with(holder.binding){
            buttonSave.tag = quotes
            shareButton.tag = quotes

            quotesText.text = quotes.quote
            quoteAuthor.text = quotes.author
            buttonSave.setOnClickListener {
                buttonSave.animate().apply {
                    duration = 1000
                    rotationY(360f)
                }.start()

                buttonSave.setColorFilter(Color.RED)
                onClickListenerSaveFavorite?.onClickSaveFavorite(quotes)
            }
            shareButton.setOnClickListener {
                onClickListenerShareQuote?.onClickShare(quotes)
            }
        }
    }
    interface OnClickListenerSaveFavorite {
        fun onClickSaveFavorite(quoteModel: QuoteModel)
    }

    interface OnClickListenerShareQuote {
        fun onClickShare(quoteModel: QuoteModel)
    }

}

