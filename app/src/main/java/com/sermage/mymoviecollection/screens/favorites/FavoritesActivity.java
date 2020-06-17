package com.sermage.mymoviecollection.screens.favorites;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;

import com.sermage.mymoviecollection.R;
import com.sermage.mymoviecollection.adapters.MovieAdapter;
import com.sermage.mymoviecollection.data.FavouriteMovie;
import com.sermage.mymoviecollection.pojo.Movie;
import com.sermage.mymoviecollection.screens.movieDetails.MovieDetailsActivity;

import java.util.ArrayList;
import java.util.List;

public class FavoritesActivity extends AppCompatActivity {

    RecyclerView recyclerViewFavourites;
    MovieAdapter adapter;
    FavoritesViewModel viewModel;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favourites);
        Toolbar toolbar=findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar=getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        recyclerViewFavourites=findViewById(R.id.recyclerViewFavourites);
        adapter=new MovieAdapter();
        recyclerViewFavourites.setLayoutManager(new GridLayoutManager(this,2));
        recyclerViewFavourites.setAdapter(adapter);
        viewModel= ViewModelProviders.of(this).get(FavoritesViewModel.class);
        viewModel.getFavouriteMovies().observe(this, new Observer<List<FavouriteMovie>>() {
            @Override
            public void onChanged(List<FavouriteMovie> favouriteMovies) {
                List<Movie> movies=new ArrayList<>();
                if(favouriteMovies!=null){
                    movies.addAll(favouriteMovies);
                    adapter.setMovies(movies);
                }
            }
        });

        adapter.setPosterListener(new MovieAdapter.OnClickMoviePosterListener() {
            @Override
            public void onClickMoviePoster(int position) {
                Movie movie=adapter.getMovies().get(position);
                Intent intent=new Intent(FavoritesActivity.this, MovieDetailsActivity.class);
                intent.putExtra("id",movie.getId());
                startActivity(intent);
            }
        });



    }
}
