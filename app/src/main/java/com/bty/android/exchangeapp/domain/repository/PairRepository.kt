package com.bty.android.exchangeapp.domain.repository

import com.bty.android.exchangeapp.data.source_result.SourceResult
import com.bty.android.exchangeapp.domain.model.CurrencyPair
import com.bty.android.exchangeapp.presentation.sort_selection.SortSelection
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

interface PairRepository {

    val sortSelection: StateFlow<SortSelection>
    val popularPairsFlow: Flow<SourceResult<List<CurrencyPair>>>
    val favouritePairsFlow: Flow<SourceResult<List<CurrencyPair>>>

    suspend fun fetchAll(code: String)
    suspend fun onSortSelectionUpdate(sortSelection: SortSelection)
}