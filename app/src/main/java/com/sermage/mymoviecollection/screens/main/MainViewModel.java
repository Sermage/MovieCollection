package com.sermage.mymoviecollection.screens.main;

import android.app.Application;
import android.os.AsyncTask;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.sermage.mymoviecollection.api.ApiFactory;
import com.sermage.mymoviecollection.api.ApiService;
import com.sermage.mymoviecollection.data.MovieDatabase;
import com.sermage.mymoviecollection.pojo.Movie;
import com.sermage.mymoviecollection.pojo.MovieResponse;

import java.util.List;
import java.util.Locale;
import java.util.concurrent.ExecutionException;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

public class MainViewModel extends AndroidViewModel {

    private static MovieDatabase database;
    private LiveData<List<Movie>> movies;
    private MutableLiveData<Throwable> errors;
    private MutableLiveData<Boolean> isLoading;
    private CompositeDisposable compositeDisposable;
    private static final String API_KEY="e95df96cf2fe57cc73fe43be0db6c773";
    private static final String MIN_VOTE_COUNT_VALUE="1000";
    private static int page;
    private static final String SORT_BY_POPULARITY="popularity.desc";
    private static final String SORT_BY_VOTE_AVERAGE="vote_average.desc";
    private static String methodSort;
    private String lang;


    public MainViewModel(@NonNull Application application) {
        super(application);
        database=MovieDatabase.getInstance(application);
        movies=database.movieDao().getAllMovies();
        errors=new MutableLiveData<>();
        isLoading=new MutableLiveData<>();
        lang= Locale.getDefault().getLanguage();
    }

    public LiveData<List<Movie>> getMovies() {
        return movies;
    }

    public LiveData<Throwable> getErrors() {
        return errors;
    }

    public LiveData<Boolean> getIsLoading() {
        return this.isLoading;
    }



    public void loadData(){
        Boolean loading=isLoading.getValue();
        if(loading!=null && loading){
            return;
        }
        isLoading.postValue(true);
        ApiFactory apiFactory=ApiFactory.getInstance();
        ApiService apiService=apiFactory.getApiService();
        compositeDisposable=new CompositeDisposable();
        Disposable disposable=apiService.getMovies(API_KEY,lang,methodSort,MIN_VOTE_COUNT_VALUE,page)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<MovieResponse>() {
                    @Override
                    public void accept(MovieResponse response) throws Exception {
                        isLoading.postValue(false);
                        if(page==1){
                            deleteAllMovies();
                        }
                        insertMovies(response.getResults());
                        page++;
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        isLoading.postValue(false);
                        errors.setValue(throwable);
                    }
                });
        compositeDisposable.add(disposable);
    }

    public void loadMoviesByPopularity(){
        page=1;
        methodSort=SORT_BY_POPULARITY;
        loadData();
    }

    public void loadMoviesByRating(){
        page=1;
        methodSort=SORT_BY_VOTE_AVERAGE;
        loadData();
    }

    @Override
    protected void onCleared() {
        if(compositeDisposable!=null) {
            compositeDisposable.dispose();
        }
        super.onCleared();
    }

    public void deleteAllMovies(){
        new DeleteAllMoviesTask().execute();
    }

    public static class DeleteAllMoviesTask extends AsyncTask<Void,Void,Void>{

        @Override
        protected Void doInBackground(Void... voids) {
            database.movieDao().deleteAllMovies();
            return null;
        }
    }

    public Movie getMovieById(int id){
        try {
            return new GetMovieTask().execute(id).get();
        } catch (ExecutionException | InterruptedException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static class GetMovieTask extends AsyncTask<Integer,Void, Movie>{

        @Override
        protected Movie doInBackground(Integer... integers) {
            if(integers!=null && integers.length>0){
                return database.movieDao().getMovieById(integers[0]);
            }
            return null;
        }
    }

    @SuppressWarnings("unchecked")
    public void insertMovies(List<Movie> movies){
        new InsertMovieTask().execute(movies);
    }

    public static class InsertMovieTask extends AsyncTask<List<Movie>,Void,Void>{

        @SuppressWarnings("unchecked")
        @Override
        protected Void doInBackground(List<Movie>... lists) {
            if(lists!=null && lists.length>0){
            database.movieDao().insertMovies(lists[0]);
            }
            return null;
        }
    }

    public void deleteMovie(Movie movie){
        new DeleteMovieTask().execute(movie);
    }

    public static class DeleteMovieTask extends AsyncTask<Movie,Void,Void>{

        @Override
        protected Void doInBackground(Movie... movies) {
            if(movies!=null && movies.length>0){
                database.movieDao().deleteMovie(movies[0]);
            }
            return null;
        }
    }


}
