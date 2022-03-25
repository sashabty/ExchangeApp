package com.bty.android.exchangeapp.domain.repository

import com.bty.android.exchangeapp.data.source_result.SourceResult
import com.bty.android.exchangeapp.domain.model.Currency
import kotlinx.coroutines.flow.StateFlow

interface CurrencyRepository {
    val currenciesFlow: StateFlow<SourceResult<List<Currency>>>
    suspend fun fetchAll()
}