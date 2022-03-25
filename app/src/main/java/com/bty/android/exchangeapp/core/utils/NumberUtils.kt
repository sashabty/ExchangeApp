package com.bty.android.exchangeapp.core.utils

import java.text.DecimalFormat

fun Double.format(numberOfDecimal: Int = 3): String =
    DecimalFormat("#.${"#".repeat(numberOfDecimal)}").format(this)