package com.sermage.mymoviecollection.screens.favorites;

import android.app.Application;
import android.os.AsyncTask;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.sermage.mymoviecollection.data.FavouriteMovie;
import com.sermage.mymoviecollection.data.MovieDatabase;

import java.util.List;
import java.util.concurrent.ExecutionException;

public class FavoritesViewModel extends AndroidViewModel {

    private static MovieDatabase database;
    private LiveData<List<FavouriteMovie>> favouriteMovies;

    public FavoritesViewModel(@NonNull Application application) {
        super(application);
        database=MovieDatabase.getInstance(application);
        favouriteMovies=database.movieDao().getAllFavouriteMovies();
    }

    public LiveData<List<FavouriteMovie>> getFavouriteMovies() {
        return favouriteMovies;
    }

    public FavouriteMovie getFavouriteMovieById(int id){
        try {
            return new GetFavouriteMovieTask().execute(id).get();
        } catch (ExecutionException | InterruptedException e) {
            e.printStackTrace();
        }
        return null;
    }

    private static class GetFavouriteMovieTask extends AsyncTask<Integer,Void,FavouriteMovie> {

        @Override
        protected FavouriteMovie doInBackground(Integer... integers) {
            if(integers!=null && integers.length>0){
                return database.movieDao().getFavouriteMovieById(integers[0]);
            }
            return null;
        }
    }

    public void insertFavouriteMovie(FavouriteMovie movie){
        new InsertFavouriteMovieTask().execute(movie);

    }

    private static class InsertFavouriteMovieTask extends AsyncTask<FavouriteMovie,Void,Void>{

        @Override
        protected Void doInBackground(FavouriteMovie... movies) {
            if(movies!=null && movies.length>0){
                database.movieDao().insertFavouriteMovie(movies[0]);
            }
            return null;
        }
    }

    public void deleteFavouriteMovie(FavouriteMovie movie){
        new DeleteFavouriteMovieTask().execute(movie);
    }

    private static class DeleteFavouriteMovieTask extends AsyncTask<FavouriteMovie,Void,Void>{

        @Override
        protected Void doInBackground(FavouriteMovie... movies) {
            if(movies!=null && movies.length>0){
                database.movieDao().deleteFavouriteMovie(movies[0]);
            }
            return null;
        }
    }
}
