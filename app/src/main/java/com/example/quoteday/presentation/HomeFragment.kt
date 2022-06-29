package com.example.quoteday.presentation

import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.quoteday.databinding.FragmentHomeBinding
import com.example.quoteday.domain.model.QuotesItem
import com.example.quoteday.presentation.viewmodel.ViewModalFactory
import com.example.quoteday.presentation.viewmodel.ViewModalHomeFragment
import javax.inject.Inject


class HomeFragment : Fragment() {


    private lateinit var binding: FragmentHomeBinding
    private lateinit var viewModel: ViewModalHomeFragment

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
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(this, viewModalFactory)[ViewModalHomeFragment::class.java]


        viewModel.getQuoteDay()

        viewModel.responseQuoteDay.observe(viewLifecycleOwner) { response ->
            if (response.isSuccessful) {
                response.body()?.let {
                    val gsonPars = it[0]
                    binding.textQuote.text = gsonPars.q
                    binding.textAuthor.text = gsonPars.a
                    binding.progressBar.visibility = View.INVISIBLE
                    clickFavoriteButton(gsonPars)
                    clickShareButton(gsonPars)
                }
            } else binding.progressBar.visibility = View.VISIBLE

        }
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

            val message = quotesItem.q
            val sendIntent: Intent = Intent().apply {
                action = Intent.ACTION_SEND
                putExtra(Intent.EXTRA_TEXT, message)
                type = "text/plain"
            }
            val shareIntent = Intent.createChooser(sendIntent, null)
            startActivity(shareIntent)
        }
    }

}