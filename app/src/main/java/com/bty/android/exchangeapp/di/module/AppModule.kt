package com.bty.android.exchangeapp.di.module

import android.content.Context
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class AppModule constructor(private val appContext: Context) {

    @Provides
    @Singleton
    fun provideContext() = appContext

}