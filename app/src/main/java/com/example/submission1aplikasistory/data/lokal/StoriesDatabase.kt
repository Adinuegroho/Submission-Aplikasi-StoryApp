package com.example.submission1aplikasistory.data.lokal

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.submission1aplikasistory.data.model.Stories

@Database(
    entities = [Stories::class],
    version = 1 ,
    exportSchema = false
)
abstract class StoriesDatabase: RoomDatabase() {

    abstract fun storiesDao(): StoriesDao

    companion object {
        @Volatile
        private var INSTANCE: StoriesDatabase? = null

        @JvmStatic
        fun getDatabase(context: Context): StoriesDatabase? {
            if (INSTANCE == null) {
                synchronized(StoriesDatabase::class) {
                    INSTANCE = Room.databaseBuilder(
                        context.applicationContext,
                        StoriesDatabase::class.java,
                        "stories_database"
                    )
                        .fallbackToDestructiveMigration()
                        .build()
                        .also { INSTANCE = it }
                }
            }
            return INSTANCE
//            return INSTANCE ?: synchronized(this) {
//                INSTANCE ?: Room.databaseBuilder(
//                    context.applicationContext,
//                    StoriesDatabase::class.java, "quote_database"
//                )
//                    .fallbackToDestructiveMigration()
//                    .build()
//                    .also { INSTANCE = it }
//            }
        }
    }
}