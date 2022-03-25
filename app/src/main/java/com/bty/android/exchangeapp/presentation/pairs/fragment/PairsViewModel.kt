package com.bty.android.exchangeapp.presentation.pairs.fragment

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bty.android.exchangeapp.data.source_result.SourceResult
import com.bty.android.exchangeapp.domain.model.CurrencyPair
import com.bty.android.exchangeapp.domain.repository.CurrencyRepository
import com.bty.android.exchangeapp.domain.repository.FavouriteRepository
import com.bty.android.exchangeapp.domain.repository.PairRepository
import com.bty.android.exchangeapp.presentation.sort_selection.SortSelection
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

class PairsViewModel @Inject constructor(
    private val currencyRepo: CurrencyRepository,
    private val pairsRepo: PairRepository,
    private val favRepo: FavouriteRepository
) : ViewModel() {

    private val _selectedCurrency = MutableStateFlow(DEFAULT_SELECTED_CURRENCY)
    val currencies: StateFlow<SourceResult<CurrencySelectionContext>>
        get() = currencyRepo.currenciesFlow.combine(_selectedCurrency) { currencies, selected ->
            currencies.map { currencyList ->
                CurrencySelectionContext(
                    currencies = currencyList,
                    selectedIndex = currencyList.indexOfFirst { it.code == selected }
                )
            }
        }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(), SourceResult.Loading)

    val popularPairs: StateFlow<SourceResult<List<CurrencyPair>>>
        get() = pairsRepo.popularPairsFlow
            .stateIn(viewModelScope, SharingStarted.WhileSubscribed(), SourceResult.Loading)

    val favouritePairs: StateFlow<SourceResult<List<CurrencyPair>>>
        get() = pairsRepo.favouritePairsFlow
            .stateIn(viewModelScope, SharingStarted.WhileSubscribed(), SourceResult.Loading)

    val sortSelection by pairsRepo::sortSelection

    companion object {
        private const val DEFAULT_SELECTED_CURRENCY = "USD"
    }

    init {
        fetchCurrencies()
    }

    fun fetchCurrencies() {
        viewModelScope.launch { currencyRepo.fetchAll() }
    }

    fun onCurrencySelected(currency: String) {
        val currencyCode = currency.split('-').first().trim()
        viewModelScope.launch { pairsRepo.fetchAll(currencyCode) }
        _selectedCurrency.tryEmit(currencyCode)
    }

    fun onFavouriteClick(item: CurrencyPair) {
        viewModelScope.launch {
            if (item.isFavourite) {
                favRepo.deselectFavourite(item)
            } else {
                favRepo.selectFavourite(item)
            }
        }
    }

    fun onSortSelectionUpdate(selection: SortSelection) {
        viewModelScope.launch { pairsRepo.onSortSelectionUpdate(selection) }
    }
}