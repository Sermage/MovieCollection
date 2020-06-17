package com.sermage.mymoviecollection.data;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.sermage.mymoviecollection.pojo.Movie;

@Database(entities = {Movie.class, FavouriteMovie.class},version = 6,exportSchema = false)
public abstract class MovieDatabase extends RoomDatabase {

    private static MovieDatabase database;
    private static final String DB_NAME="Movies.db";
    private static final Object LOCK=new Object();

    public static MovieDatabase getInstance(Context context){
        synchronized (LOCK) {
            if (database == null) {
                database = Room.databaseBuilder(context, MovieDatabase.class, DB_NAME).fallbackToDestructiveMigration().build();
            }
        }
        return database;
    }
    public abstract MovieDao movieDao();
}
