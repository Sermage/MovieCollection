package com.sermage.mymoviecollection.screens.ui.moviedetails

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.sermage.mymoviecollection.api.ApiFactory
import com.sermage.mymoviecollection.api.ApiService
import com.sermage.mymoviecollection.pojo.MovieDetails
import com.sermage.mymoviecollection.pojo.Reviews
import com.sermage.mymoviecollection.pojo.Trailers
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers

class MovieDetailsViewModel(application: Application):AndroidViewModel(application) {

    private val compositeDisposable=CompositeDisposable()
    private val reviews=MutableLiveData<List<Reviews>>()
    private val trailers=MutableLiveData<List<Trailers>>()
    private val movieDetails=MutableLiveData<MovieDetails>()

    fun getReviews():LiveData<List<Reviews>>{
        return reviews
    }
    fun getTrailers():LiveData<List<Trailers>>{
        return trailers
    }
    fun getMovieDetails():LiveData<MovieDetails>{
        return movieDetails
    }
    fun loadMovieDetails(id:Int){
        val disposable: Disposable = ApiFactory.apiService.getMovieDetails(id)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                movieDetails.value=it
            }, {

            })
        compositeDisposable.add(disposable)
    }

    fun loadReviews(id:Int){
        val disposable=ApiFactory.apiService.getReviews(id)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe ({
                reviews.value=it.reviews
            },{

        })
        compositeDisposable.add(disposable)
    }

    fun loadTrailers(id:Int){
        val disposable=ApiFactory.apiService.getTrailers(id)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                trailers.value=it.trailers
            },{

            })
        compositeDisposable.add(disposable)
    }

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.dispose()
    }
}