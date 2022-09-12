package com.example.quoteday.presentation.homefragment

import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.quoteday.R
import com.example.quoteday.databinding.FragmentHomeBinding
import com.example.quoteday.domain.model.QuotesItem
import com.example.quoteday.presentation.QuotesApplication
import com.example.quoteday.presentation.utils.ViewModalFactory
import javax.inject.Inject


class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    private lateinit var viewModel: ViewModalHomeFragment
    private lateinit var progressBar: FrameLayout
    private lateinit var errorHome: FrameLayout

    @Inject
    lateinit var viewModalFactory: ViewModalFactory
    private val component by lazy {
        (requireActivity().application as QuotesApplication).component
    }
    override fun onAttach(context: Context) {
        component.inject(this)
        super.onAttach(context)
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(this, viewModalFactory)[ViewModalHomeFragment::class.java]
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
        errorHome.visibility = View.VISIBLE
        binding.errorHomeLayout.buttonTry.setOnClickListener {
            viewModel.getQuoteDay()
            errorHome.visibility = View.INVISIBLE
        }
    }
    private fun showProgressBar() {
        progressBar.visibility = View.VISIBLE
    }
    private fun hideProgressBar() {
        progressBar.visibility = View.INVISIBLE
    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}