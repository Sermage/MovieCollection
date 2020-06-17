package com.sermage.mymoviecollection.screens.search;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.DisplayMetrics;

import com.sermage.mymoviecollection.R;
import com.sermage.mymoviecollection.adapters.MovieAdapter;
import com.sermage.mymoviecollection.pojo.Movie;
import com.sermage.mymoviecollection.screens.movieDetails.MovieDetailsActivity;

import java.util.List;

public class SearchableActivity extends AppCompatActivity {

    private MovieAdapter movieAdapter;
    private SearchViewModel viewModel;

    private int getColumnCount(){
        DisplayMetrics displayMetrics=new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        int width= (int) (displayMetrics.widthPixels/displayMetrics.density);
        return width/185 > 2? width/185 : 2;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_searchable);
        RecyclerView recyclerViewMoviePosterSearchable = findViewById(R.id.recyclerViewMoviePosterSearchable);
        SearchView searchView = findViewById(R.id.searchViewMovies);
        movieAdapter=new MovieAdapter();
        recyclerViewMoviePosterSearchable.setLayoutManager(new GridLayoutManager(this,getColumnCount()));
        recyclerViewMoviePosterSearchable.setAdapter(movieAdapter);
        viewModel= ViewModelProviders.of(this).get(SearchViewModel.class);
        viewModel.getSearchableMovies().observe(this, new Observer<List<Movie>>() {
            @Override
            public void onChanged(List<Movie> movies) {
                movieAdapter.setMovies(movies);
            }
        });
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                viewModel.loadData(query);
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                movieAdapter.clear();
                viewModel.loadData(newText);
                return true;
            }
        });

        movieAdapter.setPosterListener(new MovieAdapter.OnClickMoviePosterListener() {
            @Override
            public void onClickMoviePoster(int position) {

                Movie movie=movieAdapter.getMovies().get(position);
                Intent intent=new Intent(SearchableActivity.this, MovieDetailsActivity.class);
                intent.putExtra("id",movie.getId());
                startActivity(intent);
            }
        });

    }

}
