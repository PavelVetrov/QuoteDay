package com.example.quoteday.presentation.home

import android.content.Intent
import android.graphics.Color
import androidx.fragment.app.viewModels
import com.example.quoteday.R
import com.example.quoteday.databinding.FragmentHomeBinding
import com.example.quoteday.domain.model.QuoteModel
import com.example.quoteday.presentation.utils.BaseFragment
import com.example.quoteday.presentation.utils.gone
import com.example.quoteday.presentation.utils.visible
import dagger.hilt.android.AndroidEntryPoint

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
        viewModel.responseQuoteDay.observe(viewLifecycleOwner) { response ->
            binding.textQuote.text = response.quote
            binding.textAuthor.text = response.author
            clickFavoriteButton(response)
            clickShareButton(response)
        }
    }

    private fun clickFavoriteButton(quoteModel: QuoteModel) {
        binding.buttonFavorite.setOnClickListener {
            viewModel.addFavoriteQuote(quoteModel)
            binding.buttonFavorite.animate().apply {
                duration = 1000
                rotationY(360f)
            }.start()
            binding.buttonFavorite.setColorFilter(Color.RED)
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


