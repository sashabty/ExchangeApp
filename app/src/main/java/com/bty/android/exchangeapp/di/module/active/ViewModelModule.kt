package com.bty.android.exchangeapp.di.module.active

import com.bty.android.exchangeapp.di.scope.MainActivityScope
import com.bty.android.exchangeapp.domain.repository.CurrencyRepository
import com.bty.android.exchangeapp.domain.repository.FavouriteRepository
import com.bty.android.exchangeapp.domain.repository.PairRepository
import com.bty.android.exchangeapp.presentation.pairs.fragment.PairsViewModel
import dagger.Module
import dagger.Provides

@Module
class ViewModelModule {

    @Provides
    @MainActivityScope
    fun providePopularViewModel(
        currencyRepo: CurrencyRepository,
        pairsRepo: PairRepository,
        favouriteRepo: FavouriteRepository
    ): PairsViewModel = PairsViewModel(currencyRepo, pairsRepo, favouriteRepo)
}