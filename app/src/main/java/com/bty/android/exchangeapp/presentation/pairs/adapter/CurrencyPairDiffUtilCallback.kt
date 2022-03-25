package com.bty.android.exchangeapp.presentation.pairs.adapter

import androidx.recyclerview.widget.DiffUtil
import com.bty.android.exchangeapp.domain.model.CurrencyPair

class CurrencyPairDiffUtilCallback(
    private val oldList: List<CurrencyPair>,
    private val newList: List<CurrencyPair>
): DiffUtil.Callback() {
    override fun getOldListSize() = oldList.size

    override fun getNewListSize() = newList.size

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        val oldItem = oldList[oldItemPosition]
        val newItem = newList[newItemPosition]
        return oldItem.base == newItem.base && oldItem.secondary == newItem.secondary
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        val oldItem = oldList[oldItemPosition]
        val newItem = newList[newItemPosition]
        return oldItem.value == newItem.value && oldItem.isFavourite == newItem.isFavourite
    }
}