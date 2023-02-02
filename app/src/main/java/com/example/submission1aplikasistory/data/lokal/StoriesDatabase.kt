package com.example.submission1aplikasistory.data.lokal

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.submission1aplikasistory.data.model.Stories

@Database(
    entities = [Stories::class],
    version = 1
)
abstract class StoriesDatabase: RoomDatabase() {

    companion object {
        private var INSTANCE: StoriesDatabase? = null

        fun getDatabase(context: Context): StoriesDatabase? {
            if (INSTANCE == null) {
                synchronized(StoriesDatabase::class) {
                    INSTANCE = Room.databaseBuilder(
                        context.applicationContext,
                        StoriesDatabase::class.java,
                        "stories_database"
                    ).build()
                }
            }
            return INSTANCE
        }
    }

    abstract fun storiesDao(): StoriesDao
}