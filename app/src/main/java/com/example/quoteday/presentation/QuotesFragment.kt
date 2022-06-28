package com.example.quoteday.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.quoteday.databinding.FragmentQuotesBinding
import com.example.quoteday.domain.model.QuotesItem
import com.example.quoteday.presentation.adapter.QuotesFragmentAdapter
import com.example.quoteday.presentation.viewmodel.ViewModalQuotesFragment


class QuotesFragment : Fragment() {

    private lateinit var binding: FragmentQuotesBinding
    private lateinit var viewModel: ViewModalQuotesFragment
    private lateinit var viewAdapterQuotes: QuotesFragmentAdapter


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentQuotesBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProvider(this)[ViewModalQuotesFragment::class.java]

        initRecycleView()

        viewModel.getQuotesList()



        viewModel.getQuotesList.observe(viewLifecycleOwner) { response ->
            if (response.isSuccessful) {
                response.body()?.let {
                    viewAdapterQuotes.submitList(it)
                    binding.progressBarQuotes.visibility = View.INVISIBLE

                }
            }
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

    }
}