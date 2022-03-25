package com.bty.android.exchangeapp.data.data_source.model.mapper.fav_selection

import com.bty.android.exchangeapp.di.scope.MainActivityScope
import com.bty.android.exchangeapp.domain.model.CurrencyPair
import com.bty.android.exchangeapp.domain.model.FavouritePair
import javax.inject.Inject

@MainActivityScope
class PairToFavPairMapper @Inject constructor() {
    fun map(input: CurrencyPair): FavouritePair {
        return FavouritePair(base = input.base, secondary = input.secondary)
    }
}