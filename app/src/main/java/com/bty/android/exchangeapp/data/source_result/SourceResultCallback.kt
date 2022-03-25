package com.bty.android.exchangeapp.data.source_result

import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SourceResultCallback<T>(
    private val proxy: SourceResultCall<T>,
    private val callback: Callback<SourceResult<T>>
) : Callback<T> {
    override fun onResponse(call: Call<T>, response: Response<T>) {
        val responseBody = response.body()
        val result = when {
            !response.isSuccessful -> {
                when {
                    response.code() in 400..499 -> SourceResult.Failure(
                        status = SourceResultFailureHttpStatus.CLIENT_FAILURE
                    )
                    response.code() in 500..599 -> SourceResult.Failure(
                        status = SourceResultFailureHttpStatus.SERVER_FAILURE
                    )
                    else -> SourceResult.Failure(
                        status = SourceResultFailureHttpStatus.UNKNOWN
                    )
                }
            }
            responseBody == null -> SourceResult.Empty
            else -> SourceResult.Success(responseBody)
        }

        callback.onResponse(proxy, Response.success(result))
    }

    override fun onFailure(call: Call<T>, error: Throwable) {
        callback.onResponse(proxy, Response.success(SourceResult.Failure(exception = error)))
    }
}