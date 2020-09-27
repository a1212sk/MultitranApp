package alexander.skornyakov.multitranapp.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface HistoryDao{

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(historyItem: HistoryItem)

    @Query("DELETE FROM history_table WHERE timestamp=(SELECT min(timestamp) FROM history_table)")
    suspend fun deleteLast()

    @Query("SELECT * FROM history_table ORDER BY timestamp ASC")
    suspend fun getAll(): List<HistoryItem>

    @Query("SELECT count(*) FROM history_table")
    suspend fun count(): Int

}