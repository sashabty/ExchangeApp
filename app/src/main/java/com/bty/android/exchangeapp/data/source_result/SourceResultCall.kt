package com.bty.android.exchangeapp.data.source_result

import okhttp3.Request
import okio.Timeout
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SourceResultCall<T>(private val proxy: Call<T>) : Call<SourceResult<T>> {
    override fun enqueue(callback: Callback<SourceResult<T>>) {
        proxy.enqueue(SourceResultCallback(this, callback))
    }

    override fun clone(): Call<SourceResult<T>> {
        return SourceResultCall(proxy.clone())
    }

    override fun timeout(): Timeout {
        return proxy.timeout()
    }

    override fun execute(): Response<SourceResult<T>> = throw NotImplementedError()

    override fun cancel() = proxy.cancel()

    override fun request(): Request = proxy.request()

    override fun isExecuted() = proxy.isExecuted

    override fun isCanceled() = proxy.isCanceled
}
