package com.bty.android.exchangeapp.di.component

import com.bty.android.exchangeapp.di.module.AppModule
import com.bty.android.exchangeapp.di.module.DbModule
import com.bty.android.exchangeapp.di.module.NetworkModule
import com.bty.android.exchangeapp.di.module.SubcomponentsModule
import dagger.Component
import javax.inject.Singleton

@Component(modules = [
    AppModule::class,
    NetworkModule::class,
    DbModule::class,
    SubcomponentsModule::class
])
@Singleton
interface AppComponent {
    fun mainActivityComponent(): MainActivityComponent.Factory
}