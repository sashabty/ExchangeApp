package com.bty.android.exchangeapp.data.repository

import com.bty.android.exchangeapp.data.data_source.api.ExchangeApi
import com.bty.android.exchangeapp.data.data_source.dao.CurrencyDao
import com.bty.android.exchangeapp.data.data_source.model.mapper.CurrencyMapper
import com.bty.android.exchangeapp.data.source_result.SourceResult
import com.bty.android.exchangeapp.di.scope.MainActivityScope
import com.bty.android.exchangeapp.domain.model.Currency
import com.bty.android.exchangeapp.domain.repository.CurrencyRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject


@MainActivityScope
class CurrencyRepositoryImpl @Inject constructor(
    private val currencyDao: CurrencyDao,
    private val exchangeApi: ExchangeApi,
    private val currencyMapper: CurrencyMapper
) : CurrencyRepository {

    private val _currenciesFlow = MutableStateFlow<SourceResult<List<Currency>>>(SourceResult.Loading)
    override val currenciesFlow: StateFlow<SourceResult<List<Currency>>>
        get() = _currenciesFlow.asStateFlow()

    override suspend fun fetchAll() {
        _currenciesFlow.emit(SourceResult.Loading)
        val dbCurrencies = currencyDao.fetchAll()
        if (dbCurrencies.isNotEmpty()) {
            _currenciesFlow.emit(SourceResult.Success(dbCurrencies))
        } else {
            exchangeApi.fetchAllCurrencies().map(currencyMapper::map)
                .also { response ->
                    if (response is SourceResult.Success) {
                        response.value?.let { currencyDao.insert(it) }
                    }
                }.let { response ->
                    _currenciesFlow.emit(response)
                }
        }
    }
}