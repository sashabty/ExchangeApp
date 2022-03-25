package com.bty.android.exchangeapp.data.data_source.model.mapper.fav_selection

import com.bty.android.exchangeapp.di.scope.MainActivityScope
import com.bty.android.exchangeapp.domain.model.CurrencyPair
import com.bty.android.exchangeapp.domain.model.FavouritePair
import javax.inject.Inject

@MainActivityScope
class FavInPairsSelector @Inject constructor(
    private val pairToFavPairMapper: PairToFavPairMapper
) {
    fun map(input: List<CurrencyPair>, favs: List<FavouritePair>): List<CurrencyPair> {
        return input.map { pair -> pair.copy(isFavourite = pairToFavPairMapper.map(pair) in favs) }
    }
}