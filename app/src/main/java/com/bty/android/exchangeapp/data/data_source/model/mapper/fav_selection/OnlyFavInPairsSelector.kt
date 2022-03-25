package com.bty.android.exchangeapp.data.data_source.model.mapper.fav_selection

import com.bty.android.exchangeapp.di.scope.MainActivityScope
import com.bty.android.exchangeapp.domain.model.CurrencyPair
import com.bty.android.exchangeapp.domain.model.FavouritePair
import javax.inject.Inject


@MainActivityScope
class OnlyFavInPairsSelector @Inject constructor(
    private val pairToFavPairMapper: PairToFavPairMapper
) {
    fun map(input: List<CurrencyPair>, favs: List<FavouritePair>): List<CurrencyPair> {
        return input.mapNotNull { pair ->
            val favPair = pairToFavPairMapper.map(pair)
            if (favPair in favs) pair.copy(isFavourite = true) else null
        }
    }
}