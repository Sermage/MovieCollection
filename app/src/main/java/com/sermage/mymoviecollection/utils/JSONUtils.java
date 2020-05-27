package com.sermage.mymoviecollection.utils;


import com.sermage.mymoviecollection.data.Movie;
import com.sermage.mymoviecollection.data.Review;
import com.sermage.mymoviecollection.data.Trailer;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;


public class JSONUtils {

    private static final String RESULTS="results";
    //для отзывов
    private static final String AUTHOR="author";
    private static final String CONTENT="content";

    private static final String KEY_FOR_TRAILERS="key";
    private static final String NAME_OF_TRAILER="name";
    private static final String YOUTUBE_URL="https://www.youtube.com/watch?v=";


    //полное описание фильма
    private static final String VOTE_COUNT="vote_count";
    private static final String ID="id";
    private static final String VOTE_AVERAGE="vote_average";
    private static final String TITLE="title";
    private static final String RELEASE_DATE="release_date";
    private static final String ORIGINAL_TITLE="original_title";
    private static final String BACKDROP_PATH="backdrop_path";
    private static final String OVERVIEW="overview";
    private static final String POSTER_PATH="poster_path";

    private static final String SMALL_POSTER_SIZE="w185";
    private static final String BIG_POSTER_SIZE="w500";

    private static final String BASE_URL_POSTER="https://image.tmdb.org/t/p/";

    public static ArrayList<Movie> getMoviesFromJSON(JSONObject jsonObject){
        ArrayList<Movie> movies=new ArrayList<>();
        if(jsonObject==null){
            return movies;
        }
        try {
            JSONArray jsonArray=jsonObject.getJSONArray(RESULTS);
            for(int i=0;i<jsonArray.length();i++){
                JSONObject jsonMovie=jsonArray.getJSONObject(i);
                int id=jsonMovie.getInt(ID);
                String title=jsonMovie.getString(TITLE);
                String originalTitle=jsonMovie.getString(ORIGINAL_TITLE);
                int voteCount=jsonMovie.getInt(VOTE_COUNT);
                String releaseDate=jsonMovie.getString(RELEASE_DATE);
                String posterPath=BASE_URL_POSTER+SMALL_POSTER_SIZE+jsonMovie.getString(POSTER_PATH);
                String bigPosterPath=BASE_URL_POSTER+BIG_POSTER_SIZE+jsonMovie.getString(POSTER_PATH);
                String backdropPath=jsonMovie.getString(BACKDROP_PATH);
                double voteAverage=jsonMovie.getDouble(VOTE_AVERAGE);
                String overview=jsonMovie.getString(OVERVIEW);

                Movie movie=new Movie(id,title,originalTitle,releaseDate,overview,posterPath,bigPosterPath,voteAverage,voteCount,backdropPath);
                movies.add(movie);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
            return movies;
    }

    public static ArrayList<Review> getReviewsFromJSON(JSONObject jsonObject){
        ArrayList<Review> reviews=new ArrayList<>();
        if(jsonObject==null){
            return reviews;
        }
        try {
            JSONArray jsonArray=jsonObject.getJSONArray(RESULTS);
            for(int i=0;i<jsonArray.length();i++){
                JSONObject jsonReview=jsonArray.getJSONObject(i);
                String author=jsonReview.getString(AUTHOR);
                String content=jsonReview.getString(CONTENT);
                Review review =new Review(author,content);
                reviews.add(review);

            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return reviews;
    }

    public static ArrayList<Trailer> getTrailersFromJSON(JSONObject jsonObject){
        ArrayList<Trailer> trailers=new ArrayList<>();
        if(jsonObject==null){
            return trailers;
        }
        try {
            JSONArray jsonArray=jsonObject.getJSONArray(RESULTS);
            for(int i=0;i<jsonArray.length();i++){
                JSONObject jsonTrailer=jsonArray.getJSONObject(i);
                String key=YOUTUBE_URL+jsonTrailer.getString(KEY_FOR_TRAILERS);
                String name=jsonTrailer.getString(NAME_OF_TRAILER);
                Trailer trailer=new Trailer(key,name);
                trailers.add(trailer);

            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return trailers;
    }


}
