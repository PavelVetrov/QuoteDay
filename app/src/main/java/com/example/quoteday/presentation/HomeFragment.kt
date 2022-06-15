package com.example.quoteday.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.quoteday.databinding.FragmentHomeBinding
import com.example.quoteday.presentation.viewmodel.ViewModalHomeFragment


class HomeFragment : Fragment() {

    private lateinit var binding: FragmentHomeBinding
    private lateinit var viewModel: ViewModalHomeFragment

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(this)[ViewModalHomeFragment::class.java]


        viewModel.getQuoteDay()

        viewModel.responseQuoteDay.observe(viewLifecycleOwner) {

            if (it.isSuccessful) {
                it.body()?.let {
                    val gsonPars = it[0]
                    binding.textView.text = gsonPars.q
                    binding.progressBar.visibility = View.INVISIBLE
                }

            }

        }

    }
}