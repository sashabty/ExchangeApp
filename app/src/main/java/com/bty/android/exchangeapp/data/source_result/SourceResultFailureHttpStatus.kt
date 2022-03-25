package com.bty.android.exchangeapp.data.source_result

import androidx.annotation.StringRes
import com.bty.android.exchangeapp.R

enum class SourceResultFailureHttpStatus(@StringRes val resId: Int) {
    SERVER_FAILURE(R.string.network_result_failure_server),
    CLIENT_FAILURE(R.string.network_result_failure_client),
    UNKNOWN(R.string.network_result_failure_unknown)
}