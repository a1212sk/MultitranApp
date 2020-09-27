package alexander.skornyakov.multitranapp.data.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "history_table")
data class HistoryItem(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val timestamp: Long,
    val langFrom: Int,
    val langTo: Int,
    val word: String
)