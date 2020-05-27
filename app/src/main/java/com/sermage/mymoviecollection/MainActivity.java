package com.sermage.mymoviecollection;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.loader.app.LoaderManager;
import androidx.loader.content.Loader;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.DisplayMetrics;

import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

import android.widget.CompoundButton;
import android.widget.ProgressBar;
import android.widget.Switch;
import android.widget.TextView;
import androidx.appcompat.widget.Toolbar;


import com.sermage.mymoviecollection.adapters.MovieAdapter;
import com.sermage.mymoviecollection.data.MainViewModel;
import com.sermage.mymoviecollection.data.Movie;
import com.sermage.mymoviecollection.utils.JSONUtils;
import com.sermage.mymoviecollection.utils.NetworkUtils;

import org.json.JSONObject;

import java.net.URL;
import java.util.List;
import java.util.Locale;

public class MainActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<JSONObject> {

    private MovieAdapter movieAdapter;
    private Switch switchSortBy;
    private ProgressBar progressBarLoading;
    private TextView textViewPopularity;
    private TextView textViewTopRated;

    private MainViewModel viewModel;

    private static final int LOADER_ID=13;
    private LoaderManager loaderManager;

    private static boolean isLoading=false;
    private static int page=1;
    private static int methodSort;
    private static String lang;



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater=getMenuInflater();
        inflater.inflate(R.menu.main_menu,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id=item.getItemId();
        switch(id){
            case R.id.item_main:
                Intent intent=new Intent(this,MainActivity.class);
                startActivity(intent);
                break;
            case R.id.item_favourite:
                Intent intentToFavourites=new Intent(this,FavouritesActivity.class);
                startActivity(intentToFavourites);
                break;
            case R.id.item_search:
                Intent intentToSearchable=new Intent(this,SearchableActivity.class);
                startActivity(intentToSearchable);
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    private int getColumnCount(){
        DisplayMetrics displayMetrics=new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        int width= (int) (displayMetrics.widthPixels/displayMetrics.density);
        return width/185 > 2? width/185 : 2;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar=findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        loaderManager=LoaderManager.getInstance(this);
        lang= Locale.getDefault().getLanguage();
        switchSortBy=findViewById(R.id.switchSortBy);
        RecyclerView recyclerViewMoviePoster = findViewById(R.id.recyclerViewMoviePoster);
        textViewPopularity=findViewById(R.id.textViewSortByPopularity);
        textViewTopRated=findViewById(R.id.textViewSortByRating);
        progressBarLoading=findViewById(R.id.progressBarLoading);
        movieAdapter=new MovieAdapter();
        viewModel= ViewModelProviders.of(this).get(MainViewModel.class);
        recyclerViewMoviePoster.setLayoutManager(new GridLayoutManager(this,getColumnCount()));
        recyclerViewMoviePoster.setAdapter(movieAdapter);
        movieAdapter.setPosterListener(new MovieAdapter.OnClickMoviePosterListener() {
            @Override
            public void onClickMoviePoster(int position) {
                Movie movie=movieAdapter.getMovies().get(position);
                Intent intent=new Intent(MainActivity.this,MovieDetailsActivity.class);
                intent.putExtra("id",movie.getId());
                startActivity(intent);
                }
        });

        switchSortBy.setChecked(true);
        switchSortBy.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                page=1;
                setMethodSort(isChecked);

            }
        });
        switchSortBy.setChecked(false);

        movieAdapter.setReachEndListener(new MovieAdapter.OnReachEndListener() {
            @Override
            public void onReachEnd() {
                if(!isLoading) {
                    downloadData(methodSort, page);
                }
            }
        });
        final LiveData<List<Movie>> moviesFromLiveData=viewModel.getMovies();
        moviesFromLiveData.observe(this, new Observer<List<Movie>>() {
            @Override
            public void onChanged(List<Movie> movies) {
                if(page==1){
                    movieAdapter.setMovies(movies);
                }
            }
        });

     }


    public void onClickSetPopularity(View view) {
        setMethodSort(false);
        switchSortBy.setChecked(false);

    }

    public void onClickSetTopRated(View view) {
        setMethodSort(true);
        switchSortBy.setChecked(true);

    }

    public void setMethodSort(boolean isTopRated){
        if(isTopRated){
           methodSort=NetworkUtils.TOP_RATED;
            textViewTopRated.setTextColor(getResources().getColor(R.color.colorAccent));
            textViewPopularity.setTextColor(getResources().getColor(R.color.soft_white_color));
        }else{
            methodSort=NetworkUtils.POPULARITY;
            textViewPopularity.setTextColor(getResources().getColor(R.color.colorAccent));
            textViewTopRated.setTextColor(getResources().getColor(R.color.soft_white_color));
        }
        downloadData(methodSort,page);
    }

    private void downloadData(int methodSort,int page){
        URL url=NetworkUtils.buildURL(methodSort,page,lang);
        Bundle bundle=new Bundle();
        bundle.putString("url", String.valueOf(url));
        loaderManager.restartLoader(LOADER_ID,bundle,this);

    }


    @NonNull
    @Override
    public Loader<JSONObject> onCreateLoader(int id, @Nullable Bundle args) {
        NetworkUtils.JSONLoader jsonLoader=new NetworkUtils.JSONLoader(this,args);
        jsonLoader.setStartLoadingListener(new NetworkUtils.JSONLoader.OnStartLoadingListener() {
            @Override
            public void onStartLoading() {
                isLoading=true;
                progressBarLoading.setVisibility(View.VISIBLE);
            }
        });
        return jsonLoader;
    }

    @Override
    public void onLoadFinished(@NonNull Loader<JSONObject> loader, JSONObject data) {
        List<Movie> movies=JSONUtils.getMoviesFromJSON(data);
        if(!movies.isEmpty()){
                if(page==1) {
                    viewModel.deleteAllMovies();
                    movieAdapter.clear();
                }
            for(Movie movie:movies){
                viewModel.insertMovie(movie);
            }
            movieAdapter.AddMovies(movies);
            page++;
        }
        isLoading=false;
        loaderManager.destroyLoader(LOADER_ID);
        progressBarLoading.setVisibility(View.INVISIBLE);
    }

    @Override
    public void onLoaderReset(@NonNull Loader<JSONObject> loader) {

    }
}
