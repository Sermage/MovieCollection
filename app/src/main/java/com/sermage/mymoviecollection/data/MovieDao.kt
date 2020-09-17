package com.sermage.mymoviecollection.data

import androidx.lifecycle.LiveData
import androidx.room.*
import com.sermage.mymoviecollection.pojo.Movie
import com.sermage.mymoviecollection.pojo.TVShow

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

    @Query("SELECT*FROM favorite_tv_shows")
    fun getFavoriteTVShows():LiveData<List<TVShow>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTVShow(tvShow: TVShow)

    @Delete
    suspend fun deleteTVshow(tvShow: TVShow)

    @Query("SELECT * FROM favorite_tv_shows WHERE id==:tvShowId")
    suspend fun getTVShowById(tvShowId: Int): TVShow



}