package com.bty.android.exchangeapp.data.data_source.dao

import androidx.room.*
import com.bty.android.exchangeapp.domain.model.FavouritePair
import kotlinx.coroutines.flow.Flow

@Dao
interface FavouriteDao {

    @Query("SELECT * FROM favourites")
    fun favouritesFlow(): Flow<List<FavouritePair>>

    @Query("SELECT * FROM favourites WHERE base = :base")
    fun favouritesFlowByBase(base: String): Flow<List<FavouritePair>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(fav: FavouritePair)

    @Delete
    suspend fun delete(fav: FavouritePair)

}