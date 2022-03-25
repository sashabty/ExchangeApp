package com.bty.android.exchangeapp.domain.model

data class CurrencyPair(
    val base: String,
    val secondary: String,
    val value: Double,
    val isFavourite: Boolean
)
