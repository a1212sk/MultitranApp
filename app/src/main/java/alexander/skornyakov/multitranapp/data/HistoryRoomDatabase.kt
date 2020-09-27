package alexander.skornyakov.multitranapp.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = arrayOf(HistoryItem::class), version = 1, exportSchema = false)
abstract class HistoryRoomDatabase : RoomDatabase() {

    abstract fun historyDao(): HistoryDao

    companion object {

        private var INSTANCE: HistoryRoomDatabase? = null

        fun getDatabase(context: Context): HistoryRoomDatabase {
            val tempInstance = INSTANCE
            if (tempInstance != null) {
                return tempInstance
            }
            synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    HistoryRoomDatabase::class.java,
                    "multitranapp_history_db"
                ).build()
                INSTANCE = instance
                return instance
            }
        }
    }
}