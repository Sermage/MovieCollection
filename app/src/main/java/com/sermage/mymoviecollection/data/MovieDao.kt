package com.sermage.mymoviecollection.data

import androidx.lifecycle.LiveData
import androidx.room.*
import com.sermage.mymoviecollection.pojo.Movie

@Dao
interface MovieDao {

    @Query("SELECT*FROM favorite_movies")
    fun getFavoriteMovies():LiveData<List<Movie>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMovie(movie: Movie)

    @Delete
    suspend fun deleteMovie(movie: Movie)

    @Query("SELECT * FROM favorite_movies WHERE id==:movieId")
    suspend fun getMovieById(movieId: Int): Movie



}