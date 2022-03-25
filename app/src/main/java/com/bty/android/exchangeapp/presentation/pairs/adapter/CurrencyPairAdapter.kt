package com.bty.android.exchangeapp.presentation.pairs.adapter

import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bty.android.exchangeapp.domain.model.CurrencyPair

class CurrencyPairAdapter(
    private val currencyPairItemClickListener: CurrencyPairItemClickListener
): RecyclerView.Adapter<CurrencyPairViewHolder>() {

    private val data = mutableListOf<CurrencyPair>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        CurrencyPairViewHolder.from(parent)

    override fun onBindViewHolder(holder: CurrencyPairViewHolder, position: Int) {
        holder.bind(data[position], currencyPairItemClickListener)
    }

    override fun getItemCount() = data.size

    fun setData(items: List<CurrencyPair>) {
        val diffCallback = CurrencyPairDiffUtilCallback(data, items)
        val diffResult = DiffUtil.calculateDiff(diffCallback)
        data.clear()
        data.addAll(items)

        diffResult.dispatchUpdatesTo(this)
    }
}