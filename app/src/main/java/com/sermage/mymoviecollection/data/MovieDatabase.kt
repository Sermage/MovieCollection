package com.sermage.mymoviecollection.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.sermage.mymoviecollection.pojo.Movie
import com.sermage.mymoviecollection.pojo.TVShow

@Database(entities = [Movie::class, TVShow::class], version = 7, exportSchema = false)
abstract class MovieDatabase : RoomDatabase() {
    companion object {
        private const val DB_NAME = "movies.db"
        private var db: MovieDatabase? = null
        private val LOCK = Any()

        fun getInstance(context: Context): MovieDatabase {
            synchronized(LOCK) {
                db?.let { return it }
                val instance = Room.databaseBuilder(context, MovieDatabase::class.java, DB_NAME)
                    .fallbackToDestructiveMigration().build()
                db = instance
                return instance
            }
        }
    }

    abstract fun movieDao(): MovieDao
}