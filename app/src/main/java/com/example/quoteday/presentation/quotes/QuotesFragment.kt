package com.example.quoteday.presentation.quotes

import android.content.Intent
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.example.core.extention.gone
import com.example.core.extention.visible
import com.example.quoteday.R
import com.example.quoteday.databinding.FragmentQuotesBinding
import com.example.quoteday.domain.model.QuoteModel
import com.example.quoteday.presentation.quotes.adapter.QuotesFragmentAdapter
import com.example.core.extention.BaseFragment
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.launch

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
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.CREATED) {
                viewModel.getQuotes.filterNotNull().collect { quotes ->
                    viewAdapterQuotes?.submitList(quotes)
                }
            }
        }

    }

    private fun initRecycleView() {
        val viewAdapter = binding.rvQuotes
        viewAdapterQuotes =
            QuotesFragmentAdapter(object : QuotesFragmentAdapter.OnClickListenerQuotes {
                override fun onClickSaveFavorite(quoteModel: QuoteModel) {
                    viewModel.addFavoriteQuote(quoteModel)
                }

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
            })
        viewAdapter.adapter = viewAdapterQuotes

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