package com.sermage.mymoviecollection;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
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
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.sermage.mymoviecollection.adapters.ReviewAdapter;
import com.sermage.mymoviecollection.adapters.TrailerAdapter;
import com.sermage.mymoviecollection.data.FavouriteMovie;
import com.sermage.mymoviecollection.data.MainViewModel;
import com.sermage.mymoviecollection.data.Movie;
import com.sermage.mymoviecollection.data.Review;
import com.sermage.mymoviecollection.data.Trailer;
import com.sermage.mymoviecollection.utils.JSONUtils;
import com.sermage.mymoviecollection.utils.NetworkUtils;
import com.squareup.picasso.Picasso;

import org.json.JSONObject;


import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

public class MovieDetailsActivity extends AppCompatActivity {

    private ImageView imageViewBigPoster;
    private TextView textViewTitle;
    private TextView textViewOriginalTitle;
    private TextView textViewRating;
    private TextView textViewReleaseDate;
    private TextView textViewOverview;
    private int id;
    private MainViewModel viewModel;
    private Movie movie;
    private FavouriteMovie favouriteMovie;
    private ImageView imageViewFavourite;

    private ReviewAdapter reviewAdapter;
    private TrailerAdapter trailerAdapter;
    private RecyclerView recyclerViewReviews;
    private RecyclerView recyclerViewTrailers;

    private TextView textViewContent;
    private Button buttonSeeMore;


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
            case R.id.home:
                this.finish();
                return true;
            case R.id.item_search:
                Intent intentToSearchable=new Intent(this,SearchableActivity.class);
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
        String lang = Locale.getDefault().getLanguage();
        Toolbar toolbar=findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar=getSupportActionBar();
        actionBar.setHomeButtonEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                onBackPressed();
            }
        });
        imageViewBigPoster=findViewById(R.id.imageViewBigPoster);
        textViewTitle=findViewById(R.id.textViewTitle);
        textViewOriginalTitle=findViewById(R.id.textViewOriginalTitle);
        textViewRating=findViewById(R.id.textViewRating);
        textViewReleaseDate=findViewById(R.id.textViewReleaseDate);
        textViewOverview=findViewById(R.id.textViewOverview);
        imageViewFavourite=findViewById(R.id.imageViewFavourite);
        recyclerViewReviews=findViewById(R.id.recyclerViewReviews);
        recyclerViewTrailers=findViewById(R.id.recyclerViewTrailers);
        textViewContent=findViewById(R.id.textViewContent);
        buttonSeeMore=findViewById(R.id.buttonSeeMore);
        Intent intent=getIntent();
        if(intent!=null && intent.hasExtra("id")){
            id=intent.getIntExtra("id",-1);

        }else {
            finish();
        }
        viewModel= ViewModelProviders.of(this).get(MainViewModel.class);
        movie=viewModel.getMovieById(id);
        if(movie==null){
            movie=viewModel.getFavouriteMovieById(id);
        }
        getSupportActionBar().setTitle(movie.getTitle());
        Picasso.get().load(movie.getBigPosterPath()).placeholder(R.drawable.waiting_for_loading_poster).into(imageViewBigPoster);
        textViewTitle.setText(movie.getTitle());
        textViewOverview.setText(movie.getOverview());
        textViewReleaseDate.setText(formatReleaseDate(movie.getReleaseDate()));
        textViewOriginalTitle.setText(movie.getOriginalTitle());
        textViewRating.setText(String.format(Locale.getDefault(),"%.1f",movie.getVoteAverage()));
        setFavourite();
        JSONObject jsonObjectForReviews= NetworkUtils.getJSONForReviews(movie.getId(), lang);
        ArrayList<Review> reviews= JSONUtils.getReviewsFromJSON(jsonObjectForReviews);
        recyclerViewReviews.setLayoutManager(new LinearLayoutManager(this));
        reviewAdapter=new ReviewAdapter();
        reviewAdapter.setReviews(reviews);
        recyclerViewReviews.setAdapter(reviewAdapter);
        JSONObject jsonObjectForTrailers=NetworkUtils.getJSONForTrailers(movie.getId(), lang);
        ArrayList<Trailer> trailers=JSONUtils.getTrailersFromJSON(jsonObjectForTrailers);
        trailerAdapter=new TrailerAdapter();
        trailerAdapter.setTrailers(trailers);
        recyclerViewTrailers.setLayoutManager(new LinearLayoutManager(this));
        recyclerViewTrailers.setAdapter(trailerAdapter);
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
            viewModel.insertFavouriteMovie(new FavouriteMovie(movie));
            Toast.makeText(this, R.string.added_to_favourites, Toast.LENGTH_SHORT).show();
        }
        else{
            viewModel.deleteFavouriteMovie(favouriteMovie);
            Toast.makeText(this, R.string.deleted_from_favourites, Toast.LENGTH_SHORT).show();
        }
        setFavourite();
    }

    public void setFavourite(){
        favouriteMovie=viewModel.getFavouriteMovieById(id);
        if(favouriteMovie==null){
            imageViewFavourite.setImageResource(R.drawable.ic_star_grey_60dp);
        }else{
            imageViewFavourite.setImageResource(R.drawable.ic_star_favourite_60dp);
        }
    }
}
