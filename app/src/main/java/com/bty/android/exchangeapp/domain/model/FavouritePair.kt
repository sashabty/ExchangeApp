package com.bty.android.exchangeapp.domain.model

import androidx.room.ColumnInfo
import androidx.room.Entity

@Entity(
    tableName = FavouritePair.TABLE_NAME,
    primaryKeys = [FavouritePair.BASE_COLUMN_NAME, FavouritePair.SECONDARY_COLUMN_NAME]
)
data class FavouritePair(
    @ColumnInfo(name = BASE_COLUMN_NAME) val base: String,
    @ColumnInfo(name = SECONDARY_COLUMN_NAME) val secondary: String
) {
    companion object {
        const val TABLE_NAME = "favourites"

        const val BASE_COLUMN_NAME = "base"
        const val SECONDARY_COLUMN_NAME = "secondary"
    }
}
