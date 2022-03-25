package com.bty.android.exchangeapp.data.data_source.api

import com.bty.android.exchangeapp.data.data_source.model.CurrencyPairsResponseModel
import com.bty.android.exchangeapp.data.data_source.model.CurrencyResponseModel
import com.bty.android.exchangeapp.data.source_result.SourceResult
import retrofit2.http.GET
import retrofit2.http.Query

interface ExchangeApi {

    @GET("/currencies")
    suspend fun fetchAllCurrencies(): SourceResult<CurrencyResponseModel>

    @GET("/fetch-all")
    suspend fun fetchAllPairsByBase(@Query("from") base: String): SourceResult<CurrencyPairsResponseModel>

}