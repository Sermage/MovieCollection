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

    companion object{
       var page=1
    }

    private val compositeDisposable = CompositeDisposable()
    private val searchableMovies = MutableLiveData<List<Movie>>()
    private val searchableTvShows = MutableLiveData<List<TVShow>>()
    private val moviesByGenre=MutableLiveData<List<Movie>>()
    private val tvShowsByGenre=MutableLiveData<List<TVShow>>()
    private var isLoadingMovies=MutableLiveData<Boolean>()
    private var isLoadingTVShow=MutableLiveData<Boolean>()

    private var searchableMoviesList= mutableListOf<Movie>()
    private var searchableTVShowList= mutableListOf<TVShow>()
    private var moviesByGenreList= mutableListOf<Movie>()
    private var tvShowByGenreList= mutableListOf<TVShow>()


    fun getSearchableMovies(): MutableLiveData<List<Movie>> {
        return searchableMovies
    }

    fun getSearchableTvShows(): LiveData<List<TVShow>> {
        return searchableTvShows
    }

    fun getMoviesByGenre():LiveData<List<Movie>>{
        return moviesByGenre
    }

    fun getTVShowsByGenre():LiveData<List<TVShow>>{
        return tvShowsByGenre
    }

    fun getStatusOfLoadingMovies():LiveData<Boolean>{
        return isLoadingMovies
    }
    fun getStatusOfLoadingTV():LiveData<Boolean>{
        return isLoadingTVShow
    }

    fun getSearchingMoviesList():MutableList<Movie>{
        return searchableMoviesList
    }
    fun getSearchingTVShowList():MutableList<TVShow>{
        return searchableTVShowList
    }

    fun loadMovies(query: String) {
        val loading = isLoadingMovies.value
                if (loading != null && loading) {
                    return
                }
        isLoadingMovies.postValue(true)
        val disposable = ApiFactory.apiService.getSearchableMovies(query = query,page =page)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                isLoadingMovies.postValue(false)
                it.results?.let { it1 -> searchableMoviesList.addAll(it1) }
                searchableMovies.value = searchableMoviesList
                page++
            }, {
                isLoadingMovies.postValue(false)
            })
        compositeDisposable.add(disposable)

    }

    fun loadTvShows(query: String) {
        val loading = isLoadingTVShow.value
        if (loading != null && loading) {
            return
        }
        isLoadingTVShow.postValue(true)
        val disposable = ApiFactory.apiService.getSearchableTVShows(query = query,page = page)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                isLoadingTVShow.postValue(false)
                it.results?.let { it1 -> searchableTVShowList.addAll(it1) }
                searchableTvShows.value = searchableTVShowList
                page++
            }, {
                isLoadingTVShow.postValue(false)
            })
        compositeDisposable.add(disposable)

    }

    fun loadMoviesByGenre(genre:String){
        val disposable=ApiFactory.apiService.getMoviesByGenre(genres=genre,page=page)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                it.results?.let { it1 -> moviesByGenreList.addAll(it1) }
                moviesByGenre.value=moviesByGenreList
                page++
            },{

            })
        compositeDisposable.add(disposable)
    }

    fun loadTVShowsByGenre(genre:String){
        val disposable=ApiFactory.apiService.getTVShowsByGenre(genres=genre,page=page)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                it.results?.let { it1 -> tvShowByGenreList.addAll(it1) }
                tvShowsByGenre.value=tvShowByGenreList
                page++
            },{

            })
        compositeDisposable.add(disposable)
    }


    override fun onCleared() {
        super.onCleared()
        compositeDisposable.dispose()
    }
}