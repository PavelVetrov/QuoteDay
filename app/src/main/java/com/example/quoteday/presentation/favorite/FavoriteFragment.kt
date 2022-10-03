package com.example.quoteday.presentation.favorite

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import com.example.quoteday.databinding.FragmentFavoriteBinding
import com.example.quoteday.domain.model.QuoteModel
import com.example.quoteday.presentation.favorite.adapter.QuotesFavoriteAdapter
import com.example.quoteday.presentation.utils.BaseFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FavoriteFragment : BaseFragment<FragmentFavoriteBinding>(FragmentFavoriteBinding::inflate) {

    private val viewModal by viewModels<FavoriteQuotesViewModal>()
    private var viewAdapterFavoriteQuotes: QuotesFavoriteAdapter? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initRecycleView()
        observeFavoriteQuotes()
    }
    private fun observeFavoriteQuotes(){
        viewModal.favoriteQuotes.observe(viewLifecycleOwner) {
            viewAdapterFavoriteQuotes?.submitList(it)
        }
    }
    private fun initRecycleView() {
        val rvAdapter = binding.rvFavoriteQuotes
        viewAdapterFavoriteQuotes = QuotesFavoriteAdapter()
        rvAdapter.adapter = viewAdapterFavoriteQuotes
        viewAdapterFavoriteQuotes?.onClickListenerDeleteQuote =
            object : QuotesFavoriteAdapter.OnClickListenerDeleteQuote {
                override fun onClickDeleteQuote(quoteModel: QuoteModel) {
                    viewModal.deleteQuotes(quoteModel)
                }
            }
    }
}