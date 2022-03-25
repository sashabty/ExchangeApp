package com.bty.android.exchangeapp.data.data_source

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.bty.android.exchangeapp.data.data_source.dao.CurrencyDao
import com.bty.android.exchangeapp.data.data_source.dao.FavouriteDao
import com.bty.android.exchangeapp.domain.model.Currency
import com.bty.android.exchangeapp.domain.model.FavouritePair

@Database(
    entities = [
        FavouritePair::class,
        Currency::class
    ],
    version = AppDatabase.VERSION
)
abstract class AppDatabase : RoomDatabase() {

    abstract fun currencyDao(): CurrencyDao
    abstract fun favouriteDao(): FavouriteDao

    companion object {
        private const val DATABASE_NAME = "exchange_db"

        const val VERSION = 1

        fun from(context: Context) =
            Room.databaseBuilder(context, AppDatabase::class.java, DATABASE_NAME).build()
    }
}