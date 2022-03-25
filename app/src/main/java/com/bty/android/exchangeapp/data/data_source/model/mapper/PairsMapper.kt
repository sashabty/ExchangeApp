package com.bty.android.exchangeapp.data.data_source.model.mapper

import com.bty.android.exchangeapp.data.data_source.model.CurrencyPairsResponseModel
import com.bty.android.exchangeapp.di.scope.MainActivityScope
import com.bty.android.exchangeapp.domain.model.CurrencyPair
import javax.inject.Inject

@MainActivityScope
class PairsMapper @Inject constructor() {
    fun map(input: CurrencyPairsResponseModel?): List<CurrencyPair> {
        return input?.let { response ->
            response.results.map { pair ->
                CurrencyPair(
                    base = response.base,
                    secondary = pair.key,
                    value = pair.value,
                    isFavourite = false
                )
            }
        } ?: emptyList()
    }
}