package com.bty.android.exchangeapp.presentation.sort_selection

sealed class SortSelection {
    object AlphabetAsc: SortSelection()
    object AlphabetDesc: SortSelection()
    object ValueAsc: SortSelection()
    object ValueDesc: SortSelection()
}