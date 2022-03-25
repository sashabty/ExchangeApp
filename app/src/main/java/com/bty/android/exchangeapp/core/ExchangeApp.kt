package com.bty.android.exchangeapp.core

import android.app.Application
import com.bty.android.exchangeapp.di.component.AppComponent
import com.bty.android.exchangeapp.di.component.DaggerAppComponent
import com.bty.android.exchangeapp.di.module.AppModule

class ExchangeApp: Application() {

    lateinit var appComponent: AppComponent

    override fun onCreate() {
        super.onCreate()
        instance = this
        appComponent = DaggerAppComponent.builder().apply {
            appModule(AppModule(this@ExchangeApp))
        }.build()
    }

    companion object {
        lateinit var instance: ExchangeApp
    }
}