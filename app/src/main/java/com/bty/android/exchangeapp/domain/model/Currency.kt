package com.bty.android.exchangeapp.domain.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = Currency.TABLE_NAME)
data class Currency(
    @PrimaryKey val code: String,
    val name: String
) {
    companion object {
        const val TABLE_NAME = "currencies"
    }
}
