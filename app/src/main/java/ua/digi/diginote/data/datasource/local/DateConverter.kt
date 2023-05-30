package ua.digi.diginote.data.datasource.local

import androidx.room.TypeConverter
import java.util.*

class DateConverter {
    @TypeConverter
    fun fromTimestamp(value: Long?) = value?.let { Date(it) }
    @TypeConverter
    fun toTimestamp(date: Date?) = date?.time
}