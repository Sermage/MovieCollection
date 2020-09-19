package com.sermage.mymoviecollection.screens.ui.tvshowdetails

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.sermage.mymoviecollection.api.ApiFactory
import com.sermage.mymoviecollection.pojo.Reviews
import com.sermage.mymoviecollection.pojo.Trailers
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class TvShowViewModel(application:Application):AndroidViewModel(application) {
    private val compositeDisposable= CompositeDisposable()
    private val reviews= MutableLiveData<List<Reviews>>()
    private val trailers= MutableLiveData<List<Trailers>>()

    fun getReviews(): LiveData<List<Reviews>> {
        return reviews
    }
    fun getTrailers(): LiveData<List<Trailers>> {
        return trailers
    }

    fun loadTVShowReviews(id:Int){
        val disposable= ApiFactory.apiService.getTVShowReviews(id)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe ({
                reviews.value=it.reviews
            },{

            })
        compositeDisposable.add(disposable)
    }

    fun loadTVShowTrailers(id:Int){
        val disposable= ApiFactory.apiService.getTvShowTrailers(id)
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