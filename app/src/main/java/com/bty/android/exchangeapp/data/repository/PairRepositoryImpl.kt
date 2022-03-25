package com.bty.android.exchangeapp.data.repository

import com.bty.android.exchangeapp.data.data_source.api.ExchangeApi
import com.bty.android.exchangeapp.data.data_source.model.mapper.PairsMapper
import com.bty.android.exchangeapp.data.data_source.model.mapper.fav_selection.FavInPairsSelector
import com.bty.android.exchangeapp.data.data_source.model.mapper.fav_selection.OnlyFavInPairsSelector
import com.bty.android.exchangeapp.data.source_result.SourceResult
import com.bty.android.exchangeapp.di.scope.MainActivityScope
import com.bty.android.exchangeapp.domain.model.CurrencyPair
import com.bty.android.exchangeapp.domain.model.FavouritePair
import com.bty.android.exchangeapp.domain.repository.FavouriteRepository
import com.bty.android.exchangeapp.domain.repository.PairRepository
import com.bty.android.exchangeapp.presentation.sort_selection.SortSelection
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import javax.inject.Inject

@MainActivityScope
class PairRepositoryImpl @Inject constructor(
    private val exchangeApi: ExchangeApi,
    private val pairsMapper: PairsMapper,
    private val favInPairsSelector: FavInPairsSelector,
    private val onlyFavInPairsSelector: OnlyFavInPairsSelector,
    private val favRepo: FavouriteRepository
) : PairRepository {

    private var favouritesFlow: Flow<List<FavouritePair>> = favRepo.favourites

    private val _pairsFlow =
        MutableStateFlow<SourceResult<List<CurrencyPair>>>(SourceResult.Loading)
    override val popularPairsFlow: Flow<SourceResult<List<CurrencyPair>>>
        get() = _pairsFlow.combine(favouritesFlow) { pairsResult, favs ->
            pairsResult.map { pairs -> favInPairsSelector.map(pairs, favs) }
        }.combine(_sortSelection) { pairsResult, sortSelection ->
            pairsResult.map { pairs -> pairs.applySort(sortSelection) }
        }.flowOn(Dispatchers.IO)

    override val favouritePairsFlow: Flow<SourceResult<List<CurrencyPair>>>
        get() = _pairsFlow.combine(favouritesFlow) { pairsResult, favs ->
            val result = pairsResult.map { pairs -> onlyFavInPairsSelector.map(pairs, favs) }
            if (result is SourceResult.Success && result.value.isNullOrEmpty()) {
                SourceResult.Empty
            } else {
                result
            }
        }.combine(_sortSelection) { pairsResult, sortSelection ->
            pairsResult.map { pairs -> pairs.applySort(sortSelection) }
        }.flowOn(Dispatchers.IO)

    private val _sortSelection = MutableStateFlow<SortSelection>(SortSelection.AlphabetAsc)
    override val sortSelection: StateFlow<SortSelection>
        get() = _sortSelection

    override suspend fun fetchAll(code: String) {
        favouritesFlow = favRepo.fetchAll(code)
        val pairsResponse = exchangeApi.fetchAllPairsByBase(code)
        val pairsResult = pairsResponse.map(pairsMapper::map)
        _pairsFlow.emit(pairsResult)
    }

    override suspend fun onSortSelectionUpdate(sortSelection: SortSelection) {
        _sortSelection.emit(sortSelection)
    }

    private fun List<CurrencyPair>.applySort(sortSelection: SortSelection): List<CurrencyPair> {
        return when (sortSelection) {
            SortSelection.AlphabetAsc -> sortedBy(CurrencyPair::secondary)
            SortSelection.AlphabetDesc -> sortedByDescending(CurrencyPair::secondary)
            SortSelection.ValueAsc -> sortedBy(CurrencyPair::value)
            SortSelection.ValueDesc -> sortedByDescending(CurrencyPair::value)
        }
    }
}