package alexander.skornyakov.multitranapp.repository

import alexander.skornyakov.multitranapp.data.HistoryItem
import alexander.skornyakov.multitranapp.data.HistoryRoomDatabase
import javax.inject.Inject

class HistoryRepository @Inject constructor(val historyRoomDatabase: HistoryRoomDatabase) {
    suspend fun addHistoryItem(historyItem: HistoryItem) {
        if (historyRoomDatabase.historyDao().count() > 4) {
            historyRoomDatabase.historyDao().deleteLast()
        }
        historyRoomDatabase.historyDao().insert(historyItem)
    }

    suspend fun getAll(): List<HistoryItem> =
        historyRoomDatabase.historyDao().getAll()

}