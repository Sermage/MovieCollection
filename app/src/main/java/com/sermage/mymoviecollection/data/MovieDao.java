package com.sermage.mymoviecollection.data;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.sermage.mymoviecollection.pojo.Movie;

import java.util.List;
@Dao
public interface MovieDao {
    @Query("SELECT * FROM Movies")
    LiveData<List<Movie>> getAllMovies();

    @Query("SELECT * FROM favourite_movie")
    LiveData<List<FavouriteMovie>> getAllFavouriteMovies();

    @Query("SELECT * FROM Movies WHERE id==:movieId")
    Movie getMovieById(int movieId);

    @Query("SELECT * FROM favourite_movie WHERE id==:movieId")
    FavouriteMovie getFavouriteMovieById(int movieId);

    @Query("DELETE FROM Movies")
    void deleteAllMovies();

    @Insert (onConflict = OnConflictStrategy.REPLACE)
    void insertMovies(List<Movie> movies);

    @Insert
    void insertFavouriteMovie(FavouriteMovie movie);

    @Delete
    void deleteMovie(Movie movie);

    @Delete
    void deleteFavouriteMovie(FavouriteMovie movie);

}
