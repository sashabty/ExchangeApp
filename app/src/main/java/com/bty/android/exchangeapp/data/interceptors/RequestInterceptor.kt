package com.bty.android.exchangeapp.data.interceptors

import com.bty.android.exchangeapp.core.Const
import okhttp3.Interceptor
import okhttp3.Response
import javax.inject.Inject

class RequestInterceptor @Inject constructor(): Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request().newBuilder()
        val originalHttpUrl = chain.request().url()
        val url = originalHttpUrl.newBuilder()
            .addQueryParameter("api_key", Const.Api.API_KEY)
            .build()
        request.url(url)
        return chain.proceed(request.build())
    }
}