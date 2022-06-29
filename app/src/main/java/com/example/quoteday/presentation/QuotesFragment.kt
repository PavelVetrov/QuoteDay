package com.example.quoteday.presentation

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.quoteday.databinding.FragmentQuotesBinding
import com.example.quoteday.domain.model.QuotesItem
import com.example.quoteday.presentation.adapter.QuotesFragmentAdapter
import com.example.quoteday.presentation.viewmodel.ViewModalFactory
import com.example.quoteday.presentation.viewmodel.ViewModalQuotesFragment
import javax.inject.Inject


class QuotesFragment : Fragment() {

    private lateinit var binding: FragmentQuotesBinding
    private lateinit var viewModel: ViewModalQuotesFragment
    private lateinit var viewAdapterQuotes: QuotesFragmentAdapter

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
        binding = FragmentQuotesBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProvider(this, viewModalFactory)[ViewModalQuotesFragment::class.java]

        initRecycleView()

        viewModel.getQuotesList()

        viewModel.getQuotesList.observe(viewLifecycleOwner) { response ->
            if (response.isSuccessful) {
                response.body()?.let {
                    viewAdapterQuotes.submitList(it)
                    binding.progressBarQuotes.visibility = View.INVISIBLE

                }
            } else binding.progressBarQuotes.visibility = View.VISIBLE
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
}