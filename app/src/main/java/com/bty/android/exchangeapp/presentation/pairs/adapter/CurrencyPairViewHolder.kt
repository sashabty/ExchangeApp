package com.bty.android.exchangeapp.presentation.pairs.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bty.android.exchangeapp.core.utils.format
import com.bty.android.exchangeapp.databinding.ItemPopularBinding
import com.bty.android.exchangeapp.domain.model.CurrencyPair

class CurrencyPairViewHolder(
    private val binding: ItemPopularBinding
) : RecyclerView.ViewHolder(binding.root) {
    companion object {
        fun from(parent: ViewGroup) = CurrencyPairViewHolder(
            ItemPopularBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
        )
    }

    fun bind(item: CurrencyPair, onCurrencyPairItemClickListener: CurrencyPairItemClickListener) =
        with(binding) {
            itemPopularFav.isSelected = item.isFavourite
            itemPopularFav.setOnClickListener {
                onCurrencyPairItemClickListener.onFavouriteClick(item)
            }

            itemPopularValue.text = item.value.format()
            itemPopularPair.text = buildString {
                append(item.base)
                append(" -> ")
                append(item.secondary)
            }
        }
}