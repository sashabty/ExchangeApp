package com.bty.android.exchangeapp.data.data_source.model

import com.squareup.moshi.JsonClass
import java.util.*

@JsonClass(generateAdapter = true)
data class CurrencyPairsResponseModel(
    val base: String,
    val results: Map<String, Double>,
    val updated: Date,
    val ms: Long
)