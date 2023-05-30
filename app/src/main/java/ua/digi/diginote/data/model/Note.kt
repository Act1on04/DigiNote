package ua.digi.diginote.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*

@Entity(tableName = "notes")
data class Note(
    @PrimaryKey(autoGenerate = true)
    var id: Long = 0L,
    var title: String = "",
    var text: String = "",
    var created: Date,
    var updated: Date
)