package com.sermage.mymoviecollection.screens.movieDetails;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.sermage.mymoviecollection.R;
import com.sermage.mymoviecollection.adapters.ReviewAdapter;
import com.sermage.mymoviecollection.adapters.TrailerAdapter;
import com.sermage.mymoviecollection.data.FavouriteMovie;
import com.sermage.mymoviecollection.pojo.Movie;
import com.sermage.mymoviecollection.pojo.Reviews;
import com.sermage.mymoviecollection.pojo.Trailers;
import com.sermage.mymoviecollection.screens.favorites.FavoritesActivity;
import com.sermage.mymoviecollection.screens.favorites.FavoritesViewModel;
import com.sermage.mymoviecollection.screens.search.SearchableActivity;
import com.sermage.mymoviecollection.screens.main.MainActivity;
import com.sermage.mymoviecollection.screens.main.MainViewModel;
import com.squareup.picasso.Picasso;


import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class MovieDetailsActivity extends AppCompatActivity {

    private int id;
    private FavoritesViewModel favoritesViewModel;
    private Movie movie;
    private FavouriteMovie favouriteMovie;
    private ImageView imageViewFavourite;

    private static final String IMAGE_PATH="https://image.tmdb.org/t/p/";
    private static final String BIG_POSTER_SIZE="w500";

    private ReviewAdapter reviewAdapter;
    private TrailerAdapter trailerAdapter;


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
                Intent intent=new Intent(this, MainActivity.class);
                startActivity(intent);
                break;
            case R.id.item_favourite:
                Intent intentToFavourites=new Intent(this, FavoritesActivity.class);
                startActivity(intentToFavourites);
                break;
            case R.id.home:
                this.finish();
                return true;
            case R.id.item_search:
                Intent intentToSearchable=new Intent(this, SearchableActivity.class);
                startActivity(intentToSearchable);
                break;
        }
        return super.onOptionsItemSelected(item);
    }


    private String formatReleaseDate(String releaseDate){
        SimpleDateFormat inputDateFormat=new SimpleDateFormat("yyyy-MM-dd",Locale.getDefault());
        SimpleDateFormat outputDateFormat=new SimpleDateFormat("LLLL yyyy",Locale.getDefault());
        Date date;
        String result=null;
        try {
            date=inputDateFormat.parse(releaseDate);
            if (date != null) {
                result = outputDateFormat.format(date);
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return result;
        }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_details);
        Toolbar toolbar=findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar=getSupportActionBar();
        if(actionBar!=null) {
            actionBar.setHomeButtonEnabled(true);
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                onBackPressed();
            }
        });
        ImageView imageViewBigPoster = findViewById(R.id.imageViewBigPoster);
        TextView textViewTitle = findViewById(R.id.textViewTitle);
        TextView textViewOriginalTitle = findViewById(R.id.textViewOriginalTitle);
        TextView textViewRating = findViewById(R.id.textViewRating);
        TextView textViewReleaseDate = findViewById(R.id.textViewReleaseDate);
        TextView textViewOverview = findViewById(R.id.textViewOverview);
        imageViewFavourite=findViewById(R.id.imageViewFavourite);
        RecyclerView recyclerViewReviews = findViewById(R.id.recyclerViewReviews);
        RecyclerView recyclerViewTrailers = findViewById(R.id.recyclerViewTrailers);
        Intent intent=getIntent();
        if(intent!=null && intent.hasExtra("id")){
            id=intent.getIntExtra("id",-1);
        }else {
            finish();
        }
        MainViewModel viewModel = ViewModelProviders.of(this).get(MainViewModel.class);
        favoritesViewModel=ViewModelProviders.of(this).get(FavoritesViewModel.class);
        movie= viewModel.getMovieById(id);
        if(movie==null){
            movie=favoritesViewModel.getFavouriteMovieById(id);
        }
        getSupportActionBar().setTitle(movie.getTitle());
        Picasso.get().load(IMAGE_PATH+BIG_POSTER_SIZE+movie.getPosterPath()).placeholder(R.drawable.waiting_for_loading_poster).into(imageViewBigPoster);
        textViewTitle.setText(movie.getTitle());
        textViewOverview.setText(movie.getOverview());
        textViewReleaseDate.setText(formatReleaseDate(movie.getReleaseDate()));
        textViewOriginalTitle.setText(movie.getOriginalTitle());
        textViewRating.setText(String.format(Locale.getDefault(),"%.1f",movie.getVoteAverage()));
        setFavourite();
        //Подгрузка отзывов
        recyclerViewReviews.setLayoutManager(new LinearLayoutManager(this));
        reviewAdapter=new ReviewAdapter();
        ReviewsViewModel reviewsViewModel = ViewModelProviders.of(this).get(ReviewsViewModel.class);
        reviewsViewModel.getReviews().observe(this, new Observer<List<Reviews>>() {
            @Override
            public void onChanged(List<Reviews> reviews) {
                reviewAdapter.setReviews(reviews);
            }
        });
        reviewsViewModel.loadReviews(id);
        recyclerViewReviews.setAdapter(reviewAdapter);
        //Подгрузка трейлеров
        trailerAdapter=new TrailerAdapter();
        recyclerViewTrailers.setLayoutManager(new LinearLayoutManager(this));
        recyclerViewTrailers.setAdapter(trailerAdapter);
        TrailersViewModel trailersViewModel = ViewModelProviders.of(this).get(TrailersViewModel.class);
        trailersViewModel.getTrailers().observe(this, new Observer<List<Trailers>>() {
            @Override
            public void onChanged(List<Trailers> trailers) {
                trailerAdapter.setTrailers(trailers);
            }
        });
        trailersViewModel.loadTrailers(id);
        trailerAdapter.setTrailerClickListener(new TrailerAdapter.OnTrailerClickListener() {
            @Override
            public void onTrailerClick(String url) {
                Intent intent=new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                startActivity(intent);
            }
        });
    }

    public void onClickAddFavourite(View view) {
        if(favouriteMovie==null){
            favoritesViewModel.insertFavouriteMovie(new FavouriteMovie(movie));
            Toast.makeText(this, R.string.added_to_favourites, Toast.LENGTH_SHORT).show();
        }
        else{
            favoritesViewModel.deleteFavouriteMovie(favouriteMovie);
            Toast.makeText(this, R.string.deleted_from_favourites, Toast.LENGTH_SHORT).show();
        }
        setFavourite();
    }

    public void setFavourite(){
        favouriteMovie=favoritesViewModel.getFavouriteMovieById(id);
        if(favouriteMovie==null){
            imageViewFavourite.setImageResource(R.drawable.ic_star_grey_60dp);
        }else{
            imageViewFavourite.setImageResource(R.drawable.ic_star_favourite_60dp);
        }
    }
}
