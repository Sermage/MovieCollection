package com.sermage.mymoviecollection.screens.movieDetails;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.sermage.mymoviecollection.api.ApiFactory;
import com.sermage.mymoviecollection.api.ApiService;
import com.sermage.mymoviecollection.pojo.ReviewResponse;
import com.sermage.mymoviecollection.pojo.Reviews;
import com.sermage.mymoviecollection.screens.main.MainViewModel;

import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

public class ReviewsViewModel extends AndroidViewModel {

    private MutableLiveData<List<Reviews>> reviews;
    private static final String API_KEY="";
    private CompositeDisposable compositeDisposable;

    public ReviewsViewModel(@NonNull Application application) {
        super(application);
        reviews=new MutableLiveData<>();
    }

    public MutableLiveData<List<Reviews>> getReviews() {
        return reviews;
    }

    public void loadReviews(int id){
        ApiFactory apiFactory=ApiFactory.getInstance();
        ApiService apiService=apiFactory.getApiService();
        compositeDisposable=new CompositeDisposable();
        Disposable disposable=apiService.getReviews(id, API_KEY)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<ReviewResponse>() {
                    @Override
                    public void accept(ReviewResponse reviewResult) throws Exception {
                        reviews.setValue(reviewResult.getResults());
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {

                    }
                });
        compositeDisposable.add(disposable);
    }

    @Override
    protected void onCleared() {
        if(compositeDisposable!=null) {
            compositeDisposable.dispose();
        }
        super.onCleared();

    }
}
