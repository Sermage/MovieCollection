package com.sermage.mymoviecollection.screens.home

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


    private val listOfTrendingMovies = mutableListOf<Movie>()
    private val listOfTrendingTVShows = mutableListOf<TVShow>()
    private val listOfRatedMovies = mutableListOf<Movie>()
    private val listOfRatedTvShows = mutableListOf<TVShow>()
    private val listOfKidMovies = mutableListOf<Movie>()

    private val trendingMovies = MutableLiveData<List<Movie>>()
    private val trendingTVShows = MutableLiveData<List<TVShow>>()
    private val mostRatedMovies = MutableLiveData<List<Movie>>()
    private val mostRatedTvShow = MutableLiveData<List<TVShow>>()
    private val kidMovies = MutableLiveData<List<Movie>>()

    private val errors = MutableLiveData<Throwable>()
    private val isLoading = MutableLiveData<Boolean>()
    private val compositeDisposable = CompositeDisposable()
    private var page = 1

    fun getTrendingMovies(): LiveData<List<Movie>> {
        return trendingMovies
    }

    fun getTrendingTVShows(): LiveData<List<TVShow>> {
        return trendingTVShows
    }

    fun getRatedMovies(): LiveData<List<Movie>> {
        return mostRatedMovies
    }

    fun getRatedTVShows(): LiveData<List<TVShow>> {
        return mostRatedTvShow
    }

    fun getKidMovies(): LiveData<List<Movie>> {
        return kidMovies
    }

    fun getErrors(): LiveData<Throwable> {
        return errors
    }

    fun getStatusOfLoading(): LiveData<Boolean> {
        return isLoading
    }


    init {
        loadTrendingMovies()
        loadTrendingTVShows()
        loadRatedMovies()
        loadRatedTVShows()
        loadKidMovies()
    }


    fun loadTrendingMovies() {
        val loading = isLoading.value
        if (loading != null && loading) {
            return
        }
        isLoading.postValue(true)
        val disposable: Disposable = ApiFactory.apiService.getTrendingMovies(page = page)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                isLoading.postValue(false)
                it.results?.let { it1 -> listOfTrendingMovies.addAll(it1) }
                trendingMovies.value = listOfTrendingMovies
                page++
            }, {
                isLoading.postValue(false)
                errors.value = it

            })
        compositeDisposable.add(disposable)
    }

    fun loadTrendingTVShows() {
        val loading = isLoading.value
        if (loading != null && loading) {
            return
        }
        isLoading.postValue(true)
        val disposable: Disposable = ApiFactory.apiService.getTrendingTVShows(page = page)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                isLoading.postValue(false)
                it.results?.let { it1 -> listOfTrendingTVShows.addAll(it1) }
                trendingTVShows.value = listOfTrendingTVShows
                page++

            }, {
                isLoading.postValue(false)
                errors.value = it

            })
        compositeDisposable.add(disposable)
    }

    fun loadRatedMovies() {
        val loading = isLoading.value
        if (loading != null && loading) {
            return
        }
        isLoading.postValue(true)
        val disposable: Disposable = ApiFactory.apiService.getMostRatedMovies(page = page)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                isLoading.postValue(false)
                it.results?.let { it1 -> listOfRatedMovies.addAll(it1) }
                mostRatedMovies.value = listOfRatedMovies
                page++

            }, {
                isLoading.postValue(false)
                errors.value = it

            })
        compositeDisposable.add(disposable)
    }

    fun loadRatedTVShows() {
        val loading = isLoading.value
        if (loading != null && loading) {
            return
        }
        isLoading.postValue(true)
        val disposable: Disposable = ApiFactory.apiService.getMostRatedTvShows(page = page)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                isLoading.postValue(false)
                it.results?.let { it1 -> listOfRatedTvShows.addAll(it1) }
                mostRatedTvShow.value = listOfRatedTvShows
                page++

            }, {
                isLoading.postValue(false)
                errors.value = it

            })
        compositeDisposable.add(disposable)
    }

    fun loadKidMovies() {
        val loading = isLoading.value
        if (loading != null && loading) {
            return
        }
        isLoading.postValue(true)
        val disposable: Disposable = ApiFactory.apiService.getMovies(page = page)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                isLoading.postValue(false)
                it.results?.let { it1 -> listOfKidMovies.addAll(it1) }
                kidMovies.value = listOfKidMovies
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