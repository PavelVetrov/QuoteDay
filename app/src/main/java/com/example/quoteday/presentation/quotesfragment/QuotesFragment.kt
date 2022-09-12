package com.example.quoteday.presentation.quotesfragment

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.FrameLayout
import androidx.lifecycle.ViewModelProvider
import com.example.quoteday.R
import com.example.quoteday.databinding.FragmentQuotesBinding
import com.example.quoteday.domain.model.QuotesItem
import com.example.quoteday.presentation.QuotesApplication
import com.example.quoteday.presentation.quotesfragment.adapter.QuotesFragmentAdapter
import com.example.quoteday.presentation.utils.BaseFragment
import com.example.quoteday.presentation.utils.ViewModalFactory
import javax.inject.Inject


class QuotesFragment : BaseFragment<FragmentQuotesBinding>(FragmentQuotesBinding::inflate) {

    private lateinit var viewModel: ViewModalQuotesFragment
    private lateinit var viewAdapterQuotes: QuotesFragmentAdapter
    private var progressBar: FrameLayout? = null
    private var errorQuotes: FrameLayout? = null

    @Inject
    lateinit var viewModalFactory: ViewModalFactory
    private val component by lazy {
        (requireActivity().application as QuotesApplication).component
    }

    override fun onAttach(context: Context) {
        component.inject(this)
        super.onAttach(context)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(this, viewModalFactory)[ViewModalQuotesFragment::class.java]
        initLayout(view)
        showProgressBar()
        initRecycleView()
        observeQuotes()
    }

    private fun observeQuotes() {
        viewModel.getQuotesList()
        viewModel.viewStateQuotes.observe(viewLifecycleOwner) { viewState ->
            if (viewState.isDownload) hideProgressBar()
            else if (viewState.e != null) showException()
        }
        viewModel.getQuotesList.observe(viewLifecycleOwner) { response ->
            viewAdapterQuotes.submitList(response)
        }
    }

    private fun initRecycleView() {
        val viewAdapter = binding.rvQuotes
        viewAdapterQuotes = QuotesFragmentAdapter()
        viewAdapter.adapter = viewAdapterQuotes
        viewAdapterQuotes.onClickListenerSaveFavorite =
            object : QuotesFragmentAdapter.OnClickListenerSaveFavorite {
                override fun onClickSaveFavorite(quotesItem: QuotesItem) {
                    viewModel.addFavoriteQuote(quotesItem)
                }
            }
        viewAdapterQuotes.onClickListenerShareQuote =
            object : QuotesFragmentAdapter.OnClickListenerShareQuote {
                override fun onClickShare(quotesItem: QuotesItem) {
                    val message = quotesItem.quotes
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