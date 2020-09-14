package com.sermage.mymoviecollection.screens.ui.home

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.sermage.mymoviecollection.api.ApiFactory
import com.sermage.mymoviecollection.data.MovieDatabase
import com.sermage.mymoviecollection.pojo.Movie
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import java.util.*

class HomeViewModel(application: Application) : AndroidViewModel(application) {


    private val listOfMovies= mutableListOf<Movie>()
    private val movies=MutableLiveData<List<Movie>>()
    private val errors = MutableLiveData<Throwable>()
    private val isLoading = MutableLiveData<Boolean>()
    private var compositeDisposable = CompositeDisposable()
    private val MIN_VOTE_COUNT_VALUE = "1000"
    private var page = 1
    private val SORT_BY_POPULARITY = "popularity.desc"
    private val SORT_BY_VOTE_AVERAGE = "vote_average.desc"
    private var methodSort: String = SORT_BY_POPULARITY

    fun getMovies():LiveData<List<Movie>>{
        return movies
    }
    fun getErrors():LiveData<Throwable>{
        return errors
    }
    fun getStatusOfLoading():LiveData<Boolean>{
        return isLoading
    }

    fun loadData(){
        val loading = isLoading.value
        if (loading != null && loading) {
            return
        }
        isLoading.postValue(true)

        val disposable: Disposable = ApiFactory.apiService.getMovies(methodSort=methodSort,voteCount = MIN_VOTE_COUNT_VALUE,page= page)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                isLoading.postValue(false)
                it.results?.let { it1 -> listOfMovies.addAll(it1) }
                movies.value=listOfMovies
                page++

            }, {
                isLoading.postValue(false)
                errors.value = it

            })
        compositeDisposable.add(disposable)
    }

    fun loadMoviesByPopularity() {
        page = 1
        methodSort = SORT_BY_POPULARITY
        loadData()
    }

    fun loadMoviesByRating() {
        page = 1
        methodSort = SORT_BY_VOTE_AVERAGE
        loadData()
    }

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.dispose()
    }
}