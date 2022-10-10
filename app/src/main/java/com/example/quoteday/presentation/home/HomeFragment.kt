package com.example.quoteday.presentation.home

import android.content.Intent
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.example.quoteday.R
import com.example.quoteday.databinding.FragmentHomeBinding
import com.example.quoteday.domain.model.QuoteModel
import com.example.quoteday.presentation.utils.BaseFragment
import com.example.quoteday.presentation.utils.gone
import com.example.quoteday.presentation.utils.visible
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.launch

@AndroidEntryPoint
class HomeFragment : BaseFragment<FragmentHomeBinding>(FragmentHomeBinding::inflate) {

    private val viewModel by viewModels<HomeQuoteViewModal>()

    override fun viewBindingCreated(binding: FragmentHomeBinding) {
        showProgressBar()
        quotesObserve()
    }

    private fun quotesObserve() {
        viewModel.getQuoteDay()
        viewModel.viewState.observe(viewLifecycleOwner) { viewState ->
            when {
                viewState.isDownload -> hideProgressBar()
                viewState.error != null -> showException()
            }

        }

        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.CREATED) {
                viewModel.responseQuoteDay.filterNotNull().collect { quoteModel ->
                    binding.textQuote.text = quoteModel.quote
                    binding.textAuthor.text = quoteModel.author
                    clickFavoriteButton(quoteModel)
                    clickShareButton(quoteModel)
                }
            }
        }

    }

    private fun clickFavoriteButton(quoteModel: QuoteModel) {
        binding.radioFavoriteButtonHome.setOnClickListener {
            viewModel.addFavoriteQuote(quoteModel)
            !binding.radioFavoriteButtonHome.isChecked
        }
    }

    private fun clickShareButton(quoteModel: QuoteModel) {
        binding.shareButton.setOnClickListener {
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

    private fun showException() {
        with(binding.errorHomeLayout) {
            root.visible = true
            buttonTry.setOnClickListener {
                viewModel.getQuoteDay()
                root.visible = false
            }
        }
    }

    private fun showProgressBar() {
        binding.loadingHomeLayout.root.visible()
    }

    private fun hideProgressBar() {
        binding.loadingHomeLayout.root.gone()
    }
}


