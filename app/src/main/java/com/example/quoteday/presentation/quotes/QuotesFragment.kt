package com.example.quoteday.presentation.quotes

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.FrameLayout
import androidx.fragment.app.viewModels
import com.example.quoteday.R
import com.example.quoteday.databinding.FragmentQuotesBinding
import com.example.quoteday.domain.model.QuoteModel
import com.example.quoteday.presentation.quotes.adapter.QuotesFragmentAdapter
import com.example.quoteday.presentation.utils.BaseFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class QuotesFragment : BaseFragment<FragmentQuotesBinding>(FragmentQuotesBinding::inflate) {

    private val viewModel by viewModels<QuotesViewModal>()
    private var viewAdapterQuotes: QuotesFragmentAdapter? = null
    private var progressBar: FrameLayout? = null
    private var errorQuotes: FrameLayout? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initLayout(view)
        showProgressBar()
        initRecycleView()
        observeQuotes()
    }

    private fun observeQuotes() {
        viewModel.getQuotesList()
        viewModel.viewStateQuotes.observe(viewLifecycleOwner) { viewState ->
            when{
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

    private fun initLayout(view: View) {
        progressBar = view.findViewById(R.id.loading_quotes_layout)
        errorQuotes = view.findViewById(R.id.error_quotes_layout)
    }

    private fun showException() {
        errorQuotes?.visibility = View.VISIBLE
        binding.errorQuotesLayout.buttonTry.setOnClickListener {
            viewModel.getQuotesList()
            errorQuotes?.visibility = View.INVISIBLE
        }
    }

    private fun showProgressBar() {
        progressBar?.visibility = View.VISIBLE
    }

    private fun hideProgressBar() {
        progressBar?.visibility = View.INVISIBLE
    }

    override fun onDestroyView() {
        super.onDestroyView()
        errorQuotes = null
        progressBar = null
    }
}