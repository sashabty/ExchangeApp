package com.bty.android.exchangeapp.presentation.pairs.adapter

import com.bty.android.exchangeapp.domain.model.CurrencyPair

interface CurrencyPairItemClickListener {
    fun onFavouriteClick(item: CurrencyPair)
}