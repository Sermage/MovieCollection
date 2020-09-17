package com.sermage.mymoviecollection.screens.ui.favorites

import android.app.Application
import androidx.lifecycle.*
import com.sermage.mymoviecollection.data.MovieDatabase
import com.sermage.mymoviecollection.pojo.Movie
import com.sermage.mymoviecollection.pojo.TVShow
import kotlinx.coroutines.launch

class FavoritesViewModel(application: Application) : AndroidViewModel(application) {

    private val db=MovieDatabase.getInstance(application)
     val favoritesMovieList=db.movieDao().getFavoriteMovies()
     val favoritesTVShowList=db.movieDao().getFavoriteTVShows()

    fun insertMovieToFavorites(movie: Movie)=viewModelScope.launch {
        db.movieDao().insertMovie(movie)
    }
    fun deleteMovieFromFavorites(movie:Movie)=viewModelScope.launch {
        db.movieDao().deleteMovie(movie)
    }
    fun getMovieById(movieId:Int)=viewModelScope.launch {
        db.movieDao().getMovieById(movieId)
    }

    fun insertTVShowToFavorites(tvShow: TVShow)=viewModelScope.launch {
        db.movieDao().insertTVShow(tvShow)
    }
    fun deleteTvShowFromFavorites(tvShow: TVShow)=viewModelScope.launch {
        db.movieDao().deleteTVshow(tvShow)
    }
    fun getTvShowById(tvShowId:Int)=viewModelScope.launch {
        db.movieDao().getTVShowById(tvShowId)
    }

}