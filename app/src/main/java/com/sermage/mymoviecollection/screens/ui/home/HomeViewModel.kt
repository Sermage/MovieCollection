package com.sermage.mymoviecollection.screens.ui.home

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.sermage.mymoviecollection.api.ApiFactory
import com.sermage.mymoviecollection.pojo.Movie
import com.sermage.mymoviecollection.pojo.TVShow
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers

class HomeViewModel(application: Application) : AndroidViewModel(application) {


    private val listOfTrendingMovies= mutableListOf<Movie>()
    private val listOfTrendingTVShows= mutableListOf<TVShow>()
    private val trendingMovies=MutableLiveData<List<Movie>>()
    private val trendingTVShows=MutableLiveData<List<TVShow>>()
    private val errors = MutableLiveData<Throwable>()
    private val isLoading = MutableLiveData<Boolean>()
    private var compositeDisposable = CompositeDisposable()
    private val MIN_VOTE_COUNT_VALUE = "1000"
    private var page = 1
    private val SORT_BY_POPULARITY = "popularity.desc"
    private val SORT_BY_VOTE_AVERAGE = "vote_average.desc"
    private var methodSort: String = SORT_BY_POPULARITY

    fun getTrendingMovies():LiveData<List<Movie>>{
        return trendingMovies
    }

    fun getTrendingTVShows():LiveData<List<TVShow>>{
        return trendingTVShows
    }

    fun getErrors():LiveData<Throwable>{
        return errors
    }
    fun getStatusOfLoading():LiveData<Boolean>{
        return isLoading
    }

    fun loadTrendingMovies(){
        val loading = isLoading.value
        if (loading != null && loading) {
            return
        }
        isLoading.postValue(true)
        val disposable: Disposable = ApiFactory.apiService.getTrendingMovies(page=page)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                isLoading.postValue(false)
                it.results?.let { it1 -> listOfTrendingMovies.addAll(it1) }
                trendingMovies.value=listOfTrendingMovies
                page++
            }, {
                isLoading.postValue(false)
                errors.value = it

            })
        compositeDisposable.add(disposable)
    }

    fun loadTrendingTVShows(){
        val loading = isLoading.value
        if (loading != null && loading) {
            return
        }
        isLoading.postValue(true)
        val disposable: Disposable = ApiFactory.apiService.getTrendingTVShows(page=page)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                isLoading.postValue(false)
                it.results?.let { it1 -> listOfTrendingTVShows.addAll(it1) }
                trendingTVShows.value=listOfTrendingTVShows
                page++

            }, {
                isLoading.postValue(false)
                errors.value = it

            })
        compositeDisposable.add(disposable)
    }


    override fun onCleared() {
        super.onCleared()
        compositeDisposable.dispose()
    }
}