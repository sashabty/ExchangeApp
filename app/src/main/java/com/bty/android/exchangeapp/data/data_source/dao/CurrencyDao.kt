package com.bty.android.exchangeapp.data.data_source.dao

import androidx.room.*
import com.bty.android.exchangeapp.domain.model.Currency
import kotlinx.coroutines.flow.Flow

@Dao
interface CurrencyDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(items: List<Currency>)

    @Query("SELECT * from currencies")
    suspend fun fetchAll(): List<Currency>

}