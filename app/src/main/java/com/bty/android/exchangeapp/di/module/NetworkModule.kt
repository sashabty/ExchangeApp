package com.bty.android.exchangeapp.di.module

import com.bty.android.exchangeapp.core.Const
import com.bty.android.exchangeapp.data.adapters.DateMoshiAdapter
import com.bty.android.exchangeapp.data.interceptors.RequestInterceptor
import com.bty.android.exchangeapp.data.source_result.SourceResultAdapterFactory
import com.squareup.moshi.Moshi
import com.squareup.moshi.adapters.Rfc3339DateJsonAdapter
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import java.util.*
import javax.inject.Singleton

@Module
class NetworkModule {

    @Provides
    @Singleton
    fun provideMoshi(dateAdapter: DateMoshiAdapter): Moshi = Moshi.Builder()
        .add(Date::class.java, dateAdapter)
        .build()

    @Provides
    @Singleton
    fun provideOkHttpClient(interceptor: RequestInterceptor): OkHttpClient =
        OkHttpClient.Builder().apply {
            addInterceptor(interceptor)
        }.build()

    @Provides
    @Singleton
    fun provideRetrofit(
        moshi: Moshi,
        okHttpClient: OkHttpClient,
        networkResultAdapterFactory: SourceResultAdapterFactory
    ): Retrofit = Retrofit.Builder().apply {
        baseUrl(Const.Api.URL)
        client(okHttpClient)
        addCallAdapterFactory(networkResultAdapterFactory)
        addConverterFactory(MoshiConverterFactory.create(moshi))
    }.build()

}