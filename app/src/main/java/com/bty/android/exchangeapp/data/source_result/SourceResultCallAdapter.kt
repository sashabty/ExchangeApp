package com.bty.android.exchangeapp.data.source_result

import retrofit2.Call
import retrofit2.CallAdapter
import java.lang.reflect.Type


class SourceResultCallAdapter<R>(private val type: Type) : CallAdapter<R, Call<SourceResult<R>>> {
    override fun responseType() = type

    override fun adapt(call: Call<R>): Call<SourceResult<R>> = SourceResultCall(call)
}