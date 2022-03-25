package com.bty.android.exchangeapp.presentation.pairs.fragment

import com.bty.android.exchangeapp.domain.model.Currency

class CurrencySelectionContext(
    val currencies: List<Currency>,
    val selectedIndex: Int
)