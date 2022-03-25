package com.bty.android.exchangeapp.data.repository

import com.bty.android.exchangeapp.data.data_source.dao.FavouriteDao
import com.bty.android.exchangeapp.data.data_source.model.mapper.fav_selection.PairToFavPairMapper
import com.bty.android.exchangeapp.di.scope.MainActivityScope
import com.bty.android.exchangeapp.domain.model.CurrencyPair
import com.bty.android.exchangeapp.domain.model.FavouritePair
import com.bty.android.exchangeapp.domain.repository.FavouriteRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

@MainActivityScope
class FavouriteRepositoryImpl @Inject constructor(
    private val favouriteDao: FavouriteDao,
    private val pairToFavPairMapper: PairToFavPairMapper
): FavouriteRepository {

    private val _favourites = favouriteDao.favouritesFlow()
    override val favourites: Flow<List<FavouritePair>>
        get() = _favourites

    override suspend fun selectFavourite(item: CurrencyPair) {
        favouriteDao.insert(pairToFavPairMapper.map(item))
    }

    override suspend fun deselectFavourite(item: CurrencyPair) {
        favouriteDao.delete(pairToFavPairMapper.map(item))
    }

    override suspend fun fetchAll(code: String) = favouriteDao.favouritesFlowByBase(code)
}