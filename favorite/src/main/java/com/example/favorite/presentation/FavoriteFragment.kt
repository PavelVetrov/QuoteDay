package com.example.favorite.presentation

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.example.core.extention.BaseFragment
import com.example.favorite.databinding.FragmentFavoriteBinding
import com.example.favorite.domain.entity.QuoteModalFavorite
import com.example.favorite.presentation.adapter.QuotesFavoriteAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class FavoriteFragment :
    BaseFragment<FragmentFavoriteBinding>(inflate = FragmentFavoriteBinding::inflate) {

    private val viewModal by viewModels<FavoriteQuotesViewModal>()
    private var viewAdapterFavoriteQuotes: QuotesFavoriteAdapter? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initRecycleView()
        observeFavoriteQuotes()
    }

    private fun observeFavoriteQuotes() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.CREATED) {
                viewModal.favoriteQuotes.collect { quotesModel ->
                    viewAdapterFavoriteQuotes?.submitList(quotesModel)
                }
            }
        }
    }

    private fun initRecycleView() {
        val rvAdapter = binding.rvFavoriteQuotes
        viewAdapterFavoriteQuotes =
            QuotesFavoriteAdapter(object : QuotesFavoriteAdapter.OnClickListenerDeleteQuote {
                override fun onClickDeleteQuote(quoteModel: QuoteModalFavorite) {
                    viewModal.deleteQuotes(quoteModel)
                }
            })
        rvAdapter.adapter = viewAdapterFavoriteQuotes

    }
}