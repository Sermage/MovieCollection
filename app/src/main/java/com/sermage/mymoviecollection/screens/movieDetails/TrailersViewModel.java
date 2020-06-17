package com.sermage.mymoviecollection.screens.movieDetails;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.sermage.mymoviecollection.api.ApiFactory;
import com.sermage.mymoviecollection.api.ApiService;
import com.sermage.mymoviecollection.pojo.TrailerResponse;
import com.sermage.mymoviecollection.pojo.Trailers;

import java.util.List;
import java.util.Locale;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

public class TrailersViewModel extends AndroidViewModel {

    private MutableLiveData<List<Trailers>> trailers;
    private static final String API_KEY="e95df96cf2fe57cc73fe43be0db6c773";
    private String lang;
    private CompositeDisposable compositeDisposable;

    public TrailersViewModel(@NonNull Application application) {
        super(application);
        trailers=new MutableLiveData<>();
        lang= Locale.getDefault().getLanguage();
    }

    public MutableLiveData<List<Trailers>> getTrailers() {
        return trailers;
    }

    public void loadTrailers(int id){
        ApiFactory apiFactory=ApiFactory.getInstance();
        ApiService apiService=apiFactory.getApiService();
        compositeDisposable=new CompositeDisposable();
        Disposable disposable=apiService.getTrailers(id,API_KEY,lang)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<TrailerResponse>() {
                    @Override
                    public void accept(TrailerResponse trailerResult) throws Exception {
                        trailers.setValue(trailerResult.getTrailers());
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
