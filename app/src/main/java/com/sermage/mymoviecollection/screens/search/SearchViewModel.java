package com.sermage.mymoviecollection.screens.search;

import android.app.Application;
import android.os.AsyncTask;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.sermage.mymoviecollection.api.ApiFactory;
import com.sermage.mymoviecollection.api.ApiService;
import com.sermage.mymoviecollection.data.MovieDatabase;
import com.sermage.mymoviecollection.pojo.Movie;
import com.sermage.mymoviecollection.pojo.MovieResponse;

import java.util.List;
import java.util.Locale;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

public class SearchViewModel extends AndroidViewModel {

    private MutableLiveData<List<Movie>> searchableMovies;
    private String lang;
    private CompositeDisposable compositeDisposable;
    private static MovieDatabase database;
    private static final String API_KEY="e95df96cf2fe57cc73fe43be0db6c773";

    public SearchViewModel(@NonNull Application application) {
        super(application);
        searchableMovies=new MutableLiveData<>();
        lang= Locale.getDefault().getLanguage();
        database=MovieDatabase.getInstance(application);
    }

    public MutableLiveData<List<Movie>> getSearchableMovies() {
        return searchableMovies;
    }

    public void loadData(String query){
        ApiFactory apiFactory=ApiFactory.getInstance();
        ApiService apiService=apiFactory.getApiService();
        compositeDisposable=new CompositeDisposable();
        Disposable disposable=apiService.getSearchableMovies(API_KEY,lang,query)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<MovieResponse>() {
                    @Override
                    public void accept(MovieResponse response) throws Exception {
                        insertMovies(response.getResults());
                        searchableMovies.setValue(response.getResults());
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
        compositeDisposable.dispose();
        super.onCleared();
    }

    @SuppressWarnings("unchecked")
    public void insertMovies(List<Movie> movies){
        new InsertMovieTask().execute(movies);
    }

    public static class InsertMovieTask extends AsyncTask<List<Movie>,Void,Void> {

        @SuppressWarnings("unchecked")
        @Override
        protected Void doInBackground(List<Movie>... lists) {
            if(lists!=null && lists.length>0){
                database.movieDao().insertMovies(lists[0]);
            }
            return null;
        }
    }
}
