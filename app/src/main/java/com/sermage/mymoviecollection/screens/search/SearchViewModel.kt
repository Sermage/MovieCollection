package com.sermage.mymoviecollection.screens.search

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.sermage.mymoviecollection.api.ApiFactory
import com.sermage.mymoviecollection.pojo.Movie
import com.sermage.mymoviecollection.pojo.TVShow
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class SearchViewModel : ViewModel() {

    private val compositeDisposable = CompositeDisposable()
    private val searchableMovies = MutableLiveData<List<Movie>>()
    private val searchableTvShows = MutableLiveData<List<TVShow>>()

    fun getSearchableMovies(): LiveData<List<Movie>> {
        return searchableMovies
    }

    fun getSearchableTvShows(): LiveData<List<TVShow>> {
        return searchableTvShows
    }

    fun loadMovies(query: String) {
        val disposable = ApiFactory.apiService.getSearchableMovies(query = query)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                searchableMovies.value = it.results
            }, {

            })
        compositeDisposable.add(disposable)

    }

    fun loadTvShows(query: String) {
        val disposable = ApiFactory.apiService.getSearchableTVShows(query = query)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                searchableTvShows.value = it.results
            }, {

            })
        compositeDisposable.add(disposable)

    }


    override fun onCleared() {
        super.onCleared()
        compositeDisposable.dispose()
    }
}