package com.bty.android.exchangeapp.di.module.active

import com.bty.android.exchangeapp.data.data_source.api.ExchangeApi
import com.bty.android.exchangeapp.di.scope.MainActivityScope
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit

@Module
class MainActivityModule {

    @Provides
    @MainActivityScope
    fun provideExchangeApi(retrofit: Retrofit): ExchangeApi =
        retrofit.create(ExchangeApi::class.java)

}