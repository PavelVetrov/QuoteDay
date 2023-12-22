package com.example.catalog.presentation.adapter

import androidx.recyclerview.widget.DiffUtil
import com.example.catalog.domain.entity.QuoteModelCatalog

class QuotesCatalogFragmentDiffCallBack : DiffUtil.ItemCallback<QuoteModelCatalog>() {

    override fun areItemsTheSame(oldItem: QuoteModelCatalog, newItem: QuoteModelCatalog): Boolean {
        return oldItem.id == newItem.id
    }
    override fun areContentsTheSame(oldItem: QuoteModelCatalog, newItem: QuoteModelCatalog): Boolean {
        return oldItem == newItem
    }
}