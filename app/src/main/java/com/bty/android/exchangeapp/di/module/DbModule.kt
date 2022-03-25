package com.bty.android.exchangeapp.di.module

import android.content.Context
import com.bty.android.exchangeapp.data.data_source.AppDatabase
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class DbModule {

    @Provides
    @Singleton
    fun provideDatabase(context: Context): AppDatabase = AppDatabase.from(context)

}