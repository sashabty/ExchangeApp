package com.bty.android.exchangeapp.data.source_result

import retrofit2.Call
import retrofit2.CallAdapter
import retrofit2.Retrofit
import java.lang.reflect.ParameterizedType
import java.lang.reflect.Type
import javax.inject.Inject

class SourceResultAdapterFactory @Inject constructor(): CallAdapter.Factory() {

    override fun get(
        returnType: Type,
        annotations: Array<out Annotation>,
        retrofit: Retrofit
    ): CallAdapter<*, *>? {
        val callInnerType = returnType.findCallInnerType()
        val callRawInnerType = callInnerType?.let { getRawType(it) }

        return when {
            callRawInnerType != SourceResult::class.java -> null
            callInnerType is ParameterizedType -> {
                val resultInnerType = getParameterUpperBound(0, callInnerType)
                SourceResultCallAdapter<Any?>(resultInnerType)
            }
            else -> SourceResultCallAdapter<Nothing>(Nothing::class.java)
        }
    }

    private fun Type.findCallInnerType(): Type? {
        val rawType: Class<*> = getRawType(this)
        return if (rawType == Call::class.java && this is ParameterizedType) {
            getParameterUpperBound(0, this)
        } else {
            null
        }
    }
}