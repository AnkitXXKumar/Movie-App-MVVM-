package com.example.notesapp.RoomSetup

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters

@Database(
    entities = [Movies::class],
    version = 1,
    exportSchema = false
)
abstract class MoviesDatabase : RoomDatabase() {

    abstract fun moviesdao():MoviesDao

    companion object {
        @Volatile
        private var INSTANCE: MoviesDatabase? = null
        private val LOCK = Any()

        fun getDatabase(context: Context): MoviesDatabase =
            INSTANCE ?: synchronized(LOCK) {
                INSTANCE
                    ?: buildDatabase(context).also { INSTANCE = it }
            }

        private fun buildDatabase(context: Context) =
            Room.databaseBuilder(
                context.applicationContext,
                MoviesDatabase::class.java, "movies_database.db"
            ).build()
    }
}