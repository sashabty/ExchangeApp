package com.bty.android.exchangeapp.di.module.active

import com.bty.android.exchangeapp.data.data_source.AppDatabase
import com.bty.android.exchangeapp.data.data_source.dao.CurrencyDao
import com.bty.android.exchangeapp.data.data_source.dao.FavouriteDao
import com.bty.android.exchangeapp.di.scope.MainActivityScope
import dagger.Module
import dagger.Provides

@Module
class DaoModule {

    @Provides
    @MainActivityScope
    fun provideCurrencyDao(db: AppDatabase): CurrencyDao = db.currencyDao()

    @Provides
    @MainActivityScope
    fun provideFavouriteDao(db: AppDatabase): FavouriteDao = db.favouriteDao()
}