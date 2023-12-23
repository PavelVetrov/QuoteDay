package com.example.favorite.presentation.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import com.example.favorite.R
import com.example.favorite.databinding.QuotesFavoriteItemBinding
import com.example.favorite.domain.entity.QuoteModalFavorite

class QuotesFavoriteAdapter(
    private val onClickListenerDeleteQuote: OnClickListenerDeleteQuote
) :
    ListAdapter<QuoteModalFavorite, QuotesFavoriteViewHolder>(QuotesFragmentDiffCallBack()),
    View.OnClickListener {

    override fun onClick(v: View) {
        val quoteModel = v.tag as QuoteModalFavorite
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

        fun onClickDeleteQuote(quoteModel: QuoteModalFavorite)
    }
}