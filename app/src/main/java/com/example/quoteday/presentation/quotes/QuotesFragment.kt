package com.example.quoteday.presentation.quotes

import android.content.Intent
import androidx.fragment.app.viewModels
import com.example.quoteday.R
import com.example.quoteday.databinding.FragmentQuotesBinding
import com.example.quoteday.domain.model.QuoteModel
import com.example.quoteday.presentation.quotes.adapter.QuotesFragmentAdapter
import com.example.quoteday.presentation.utils.BaseFragment
import com.example.quoteday.presentation.utils.gone
import com.example.quoteday.presentation.utils.visible
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class QuotesFragment : BaseFragment<FragmentQuotesBinding>(FragmentQuotesBinding::inflate) {

    private val viewModel by viewModels<QuotesViewModal>()
    private var viewAdapterQuotes: QuotesFragmentAdapter? = null

    override fun viewBindingCreated(binding: FragmentQuotesBinding) {
        showProgressBar()
        initRecycleView()
        observeQuotes()
    }

    private fun observeQuotes() {
        viewModel.getQuotesList()
        viewModel.viewStateQuotes.observe(viewLifecycleOwner) { viewState ->
            when {
                viewState.isDownload -> hideProgressBar()
                viewState.error != null -> showException()
            }
        }
        viewModel.getQuotesList.observe(viewLifecycleOwner) { response ->
            viewAdapterQuotes?.submitList(response)
        }
    }

    private fun initRecycleView() {
        val viewAdapter = binding.rvQuotes
        viewAdapterQuotes = QuotesFragmentAdapter()
        viewAdapter.adapter = viewAdapterQuotes
        viewAdapterQuotes?.onClickListenerSaveFavorite =
            object : QuotesFragmentAdapter.OnClickListenerSaveFavorite {
                override fun onClickSaveFavorite(quoteModel: QuoteModel) {
                    viewModel.addFavoriteQuote(quoteModel)
                }
            }
        viewAdapterQuotes?.onClickListenerShareQuote =
            object : QuotesFragmentAdapter.OnClickListenerShareQuote {
                override fun onClickShare(quoteModel: QuoteModel) {
                    val message = quoteModel.quote
                    val sendIntent: Intent = Intent().apply {
                        action = Intent.ACTION_SEND
                        putExtra(Intent.EXTRA_TEXT, message)
                        type = getString(R.string.text_plain)
                    }
                    val shareIntent = Intent.createChooser(sendIntent, null)
                    startActivity(shareIntent)
                }
            }
    }

    private fun showException() {
        with(binding.errorQuotesLayout) {
            root.visible = true
            buttonTry.setOnClickListener {
                viewModel.getQuotesList()
                root.visible = false
            }
        }
    }

    private fun showProgressBar() {
        binding.loadingQuotesLayout.root.visible()
    }

    private fun hideProgressBar() {
        binding.loadingQuotesLayout.root.gone()
    }

}