package com.bty.android.exchangeapp.data.data_source.model

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class CurrencyResponseModel(
    val currencies: Map<String, String>,
    val ms: Long
)