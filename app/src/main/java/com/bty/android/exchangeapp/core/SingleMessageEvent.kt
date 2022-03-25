package com.bty.android.exchangeapp.core

import androidx.annotation.StringRes

class SingleMessageEvent(@StringRes val resId: Int) {
    companion object {
        fun default() = SingleMessageEvent(0)
    }
}