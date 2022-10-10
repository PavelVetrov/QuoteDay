package com.example.quoteday.presentation.favorite.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import com.example.quoteday.R
import com.example.quoteday.databinding.QuotesFavoriteItemBinding
import com.example.quoteday.domain.model.QuoteModel
import com.example.quoteday.presentation.utils.QuotesFragmentDiffCallBack

class QuotesFavoriteAdapter(
    private val onClickListenerDeleteQuote: OnClickListenerDeleteQuote
) :
    ListAdapter<QuoteModel, QuotesFavoriteViewHolder>(QuotesFragmentDiffCallBack()),
    View.OnClickListener {

    override fun onClick(v: View) {
        val quoteModel = v.tag as QuoteModel
        when (v.id) {
            R.id.bottom_delete -> onClickListenerDeleteQuote.onClickDeleteQuote(quoteModel)
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): QuotesFavoriteViewHolder {
        val binding =
            QuotesFavoriteItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)

        binding.bottomDelete.setOnClickListener(this)

        return QuotesFavoriteViewHolder(binding)
    }

    override fun onBindViewHolder(holder: QuotesFavoriteViewHolder, position: Int) {
        val getQuote = getItem(position)
        with(holder.binding) {
            bottomDelete.tag = getQuote
            quotesFavoriteText.text = getQuote.quote
            quoteFavoriteAuthor.text = getQuote.author
        }
    }

    interface OnClickListenerDeleteQuote {

        fun onClickDeleteQuote(quoteModel: QuoteModel)
    }
}