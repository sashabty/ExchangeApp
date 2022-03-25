package com.bty.android.exchangeapp.di.module.active

import com.bty.android.exchangeapp.data.repository.CurrencyRepositoryImpl
import com.bty.android.exchangeapp.data.repository.FavouriteRepositoryImpl
import com.bty.android.exchangeapp.data.repository.PairRepositoryImpl
import com.bty.android.exchangeapp.di.scope.MainActivityScope
import com.bty.android.exchangeapp.domain.repository.CurrencyRepository
import com.bty.android.exchangeapp.domain.repository.FavouriteRepository
import com.bty.android.exchangeapp.domain.repository.PairRepository
import dagger.Binds
import dagger.Module

@Module
abstract class RepositoryModule {

    @Binds
    @MainActivityScope
    abstract fun provideCurrencyRepo(currencyRepositoryImpl: CurrencyRepositoryImpl): CurrencyRepository

    @Binds
    @MainActivityScope
    abstract fun providePairsRepo(pairRepositoryImpl: PairRepositoryImpl): PairRepository

    @Binds
    @MainActivityScope
    abstract fun provideFavouriteRepo(favouriteRepositoryImpl: FavouriteRepositoryImpl): FavouriteRepository
}