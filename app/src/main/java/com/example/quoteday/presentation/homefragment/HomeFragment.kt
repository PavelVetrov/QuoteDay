package com.example.quoteday.presentation.homefragment

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.view.View
import android.widget.FrameLayout
import androidx.fragment.app.viewModels
import com.example.quoteday.R
import com.example.quoteday.databinding.FragmentHomeBinding
import com.example.quoteday.domain.model.QuotesItem
import com.example.quoteday.presentation.utils.BaseFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : BaseFragment<FragmentHomeBinding>(FragmentHomeBinding::inflate) {

    private val viewModel by viewModels<ViewModalHomeFragment>()
    private var progressBar: FrameLayout? = null
    private var errorHome: FrameLayout? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initLayout(view)
        showProgressBar()
        quotesObserve()
    }
    private fun quotesObserve() {
        viewModel.getQuoteDay()
        viewModel.viewState.observe(viewLifecycleOwner) { viewState ->
            if (viewState.isDownload) hideProgressBar()
            else if (viewState.e != null) showException()
        }
        viewModel.responseQuoteDay.observe(viewLifecycleOwner) { response ->
            binding.textQuote.text = response.quotes
            binding.textAuthor.text = response.author
            clickFavoriteButton(response)
            clickShareButton(response)
        }
    }
    private fun initLayout(view: View) {
        progressBar = view.findViewById(R.id.loading_home_layout)
        errorHome = view.findViewById(R.id.error_home_layout)
    }
    private fun clickFavoriteButton(quotesItem: QuotesItem) {
        binding.buttonFavorite.setOnClickListener {
            viewModel.addFavoriteQuote(quotesItem)
            binding.buttonFavorite.animate().apply {
                duration = 1000
                rotationY(360f)
            }.start()
            binding.buttonFavorite.setColorFilter(Color.RED)
        }
    }
    private fun clickShareButton(quotesItem: QuotesItem) {
        binding.shareButton.setOnClickListener {
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
    private fun showException() {
        errorHome?.visibility = View.VISIBLE
        binding.errorHomeLayout.buttonTry.setOnClickListener {
            viewModel.getQuoteDay()
            errorHome?.visibility = View.INVISIBLE
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
        errorHome = null
        progressBar = null
    }
}