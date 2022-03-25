package com.bty.android.exchangeapp.data.data_source.model.mapper

import com.bty.android.exchangeapp.data.data_source.model.CurrencyResponseModel
import com.bty.android.exchangeapp.di.scope.MainActivityScope
import com.bty.android.exchangeapp.domain.model.Currency
import javax.inject.Inject

@MainActivityScope
class CurrencyMapper @Inject constructor() {
    fun map(input: CurrencyResponseModel?): List<Currency> {
        return input?.currencies?.entries?.map { currencyData ->
            Currency(code = currencyData.key, name = currencyData.value)
        } ?: emptyList()
    }
}