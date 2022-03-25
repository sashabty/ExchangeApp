package com.bty.android.exchangeapp.data.adapters

import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.JsonReader
import com.squareup.moshi.JsonWriter
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject

class DateMoshiAdapter @Inject constructor(): JsonAdapter<Date>() {

    private val dateFormatter = SimpleDateFormat(DATE_FORMAT, Locale.getDefault())

    companion object {
        private const val DATE_FORMAT = "yyyy-MM-dd hh:mm:ss"
    }

    override fun fromJson(reader: JsonReader): Date? {
        if (reader.peek() == JsonReader.Token.NULL) {
            return reader.nextNull()
        }
        val string = reader.nextString()
        return dateFormatter.parse(string)
    }

    override fun toJson(writer: JsonWriter, value: Date?) {
        if (value == null) {
            writer.nullValue()
        } else {
            val string = dateFormatter.format(value)
            writer.value(string)
        }
    }
}