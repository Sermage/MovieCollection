package com.sermage.mymoviecollection.screens.ui.favorites

import android.app.Application
import androidx.lifecycle.*
import com.sermage.mymoviecollection.data.MovieDatabase
import com.sermage.mymoviecollection.pojo.Movie
import kotlinx.coroutines.launch

class FavoritesViewModel(application: Application) : AndroidViewModel(application) {

    private val db=MovieDatabase.getInstance(application)
     val favoritesList=db.movieDao().getFavoriteMovies()

    fun insertMovieToFavorites(movie: Movie)=viewModelScope.launch {
        db.movieDao().insertMovie(movie)
    }
    fun deleteMovieFromFavorites(movie:Movie)=viewModelScope.launch {
        db.movieDao().deleteMovie(movie)
    }
    fun getMovieById(movieId:Int)=viewModelScope.launch {
        db.movieDao().getMovieById(movieId)
    }

}