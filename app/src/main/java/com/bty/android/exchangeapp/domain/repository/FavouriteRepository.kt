package com.bty.android.exchangeapp.domain.repository

import com.bty.android.exchangeapp.domain.model.CurrencyPair
import com.bty.android.exchangeapp.domain.model.FavouritePair
import kotlinx.coroutines.flow.Flow

interface FavouriteRepository {

    val favourites: Flow<List<FavouritePair>>

    suspend fun selectFavourite(item: CurrencyPair)
    suspend fun deselectFavourite(item: CurrencyPair)
    suspend fun fetchAll(code: String): Flow<List<FavouritePair>>
}