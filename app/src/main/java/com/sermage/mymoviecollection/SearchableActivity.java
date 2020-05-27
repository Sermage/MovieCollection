package com.sermage.mymoviecollection;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.DisplayMetrics;

import com.sermage.mymoviecollection.adapters.MovieAdapter;
import com.sermage.mymoviecollection.data.MainViewModel;
import com.sermage.mymoviecollection.data.Movie;
import com.sermage.mymoviecollection.utils.JSONUtils;
import com.sermage.mymoviecollection.utils.NetworkUtils;

import org.json.JSONObject;

import java.util.List;
import java.util.Locale;

public class SearchableActivity extends AppCompatActivity {

   private SearchView searchView;
   private MovieAdapter movieAdapter;
   private RecyclerView recyclerViewMoviePosterSearchable;
   private String lang;
   private MainViewModel viewModel;

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
        recyclerViewMoviePosterSearchable=findViewById(R.id.recyclerViewMoviePosterSearchable);
        searchView=findViewById(R.id.searchViewMovies);
        lang = Locale.getDefault().getLanguage();
        movieAdapter=new MovieAdapter();
        recyclerViewMoviePosterSearchable.setLayoutManager(new GridLayoutManager(this,getColumnCount()));
        recyclerViewMoviePosterSearchable.setAdapter(movieAdapter);
        viewModel= ViewModelProviders.of(this).get(MainViewModel.class);
        movieAdapter.setPosterListener(new MovieAdapter.OnClickMoviePosterListener() {
            @Override
            public void onClickMoviePoster(int position) {

                Movie movie=movieAdapter.getMovies().get(position);
                Intent intent=new Intent(SearchableActivity.this,MovieDetailsActivity.class);
                intent.putExtra("id",movie.getId());
                startActivity(intent);
            }
        });
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                downloadData(query);
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                movieAdapter.clear();
                downloadData(newText);
                return true;
            }
        });

    }

    private void downloadData(String query){
        JSONObject jsonObject=NetworkUtils.getJSONForSearchableMovies(query,lang);
        List<Movie> searchableMovies= JSONUtils.getMoviesFromJSON(jsonObject);
        movieAdapter.setMovies(searchableMovies);
        if(!searchableMovies.isEmpty()){
            viewModel.deleteAllMovies();
        }
        for(Movie movie:searchableMovies){
            viewModel.insertMovie(movie);
        }
    }
}
