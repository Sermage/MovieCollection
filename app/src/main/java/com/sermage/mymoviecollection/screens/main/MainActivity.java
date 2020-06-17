package com.sermage.mymoviecollection.screens.main;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
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
import android.widget.Toast;

import androidx.appcompat.widget.Toolbar;


import com.sermage.mymoviecollection.R;
import com.sermage.mymoviecollection.adapters.MovieAdapter;
import com.sermage.mymoviecollection.pojo.Movie;
import com.sermage.mymoviecollection.screens.favorites.FavoritesActivity;
import com.sermage.mymoviecollection.screens.movieDetails.MovieDetailsActivity;
import com.sermage.mymoviecollection.screens.search.SearchableActivity;

import java.util.List;

public class MainActivity extends AppCompatActivity  {

    private MovieAdapter movieAdapter;
    private Switch switchSortBy;
    private ProgressBar progressBarLoading;
    private TextView textViewPopularity;
    private TextView textViewTopRated;
    private MainViewModel viewModel;
    private boolean isLoading;



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
                Intent intentToFavourites=new Intent(this, FavoritesActivity.class);
                startActivity(intentToFavourites);
                break;
            case R.id.item_search:
                Intent intentToSearchable=new Intent(this, SearchableActivity.class);
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
        switchSortBy=findViewById(R.id.switchSortBy);
        RecyclerView recyclerViewMoviePoster = findViewById(R.id.recyclerViewMoviePoster);
        textViewPopularity=findViewById(R.id.textViewSortByPopularity);
        textViewTopRated=findViewById(R.id.textViewSortByRating);
        progressBarLoading=findViewById(R.id.progressBarLoading);
        movieAdapter=new MovieAdapter();
        viewModel= ViewModelProviders.of(this).get(MainViewModel.class);
        recyclerViewMoviePoster.setLayoutManager(new GridLayoutManager(this,getColumnCount()));
        recyclerViewMoviePoster.setAdapter(movieAdapter);
        viewModel.getMovies().observe(this, new Observer<List<Movie>>() {
            @Override
            public void onChanged(List<Movie> movies) {
                movieAdapter.setMovies(movies);
            }
        });
        viewModel.getErrors().observe(this, new Observer<Throwable>() {
            @Override
            public void onChanged(Throwable throwable) {
                Toast.makeText(MainActivity.this, throwable.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
        viewModel.getIsLoading().observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean aBoolean) {
                setLoading(aBoolean);
            }
        });
                movieAdapter.setPosterListener(new MovieAdapter.OnClickMoviePosterListener() {
            @Override
            public void onClickMoviePoster(int position) {
                Movie movie=movieAdapter.getMovies().get(position);
                Intent intent=new Intent(MainActivity.this, MovieDetailsActivity.class);
                intent.putExtra("id",movie.getId());
                startActivity(intent);
                }
        });

        switchSortBy.setChecked(true);
        switchSortBy.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                setMethodSort(isChecked);
            }
        });
        switchSortBy.setChecked(false);
        movieAdapter.setReachEndListener(new MovieAdapter.OnReachEndListener() {
            @Override
            public void onReachEnd() {
                if(!isLoading){
                    viewModel.loadData();
                }
            }
        });
     }

    public void setMethodSort(boolean isTopRated){
        if (isTopRated && !isLoading) {
            movieAdapter.clear();
            viewModel.loadMoviesByRating();
            textViewTopRated.setTextColor(getResources().getColor(R.color.colorAccent));
            textViewPopularity.setTextColor(getResources().getColor(R.color.soft_white_color));
        } else if(!isTopRated && !isLoading) {
            movieAdapter.clear();
            viewModel.loadMoviesByPopularity();
            textViewPopularity.setTextColor(getResources().getColor(R.color.colorAccent));
            textViewTopRated.setTextColor(getResources().getColor(R.color.soft_white_color));
        }
    }

    public void onClickSetPopularity(View view) {
        setMethodSort(false);
        switchSortBy.setChecked(false);

    }

    public void onClickSetTopRated(View view) {
        setMethodSort(true);
        switchSortBy.setChecked(true);

    }

       public void setLoading(boolean loading) {
        isLoading = loading;
        if(isLoading){
            progressBarLoading.setVisibility(View.VISIBLE);
        }
        else{
            progressBarLoading.setVisibility(View.INVISIBLE);
        }
    }
}
