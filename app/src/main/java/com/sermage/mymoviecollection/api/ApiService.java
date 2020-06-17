package com.sermage.mymoviecollection.api;

import com.sermage.mymoviecollection.pojo.MovieResponse;
import com.sermage.mymoviecollection.pojo.ReviewResponse;
import com.sermage.mymoviecollection.pojo.TrailerResponse;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ApiService {

    @GET("discover/movie")
    Observable<MovieResponse> getMovies(@Query("api_key") String apiKey, @Query("language") String lang, @Query("sort_by") String methodSort, @Query("vote_count.gte") String voteCount, @Query("page") int page);

    @GET("movie/{id}/reviews")
    Observable<ReviewResponse> getReviews(@Path("id") int id, @Query("api_key") String apiKey);

    @GET("movie/{id}/videos")
    Observable<TrailerResponse> getTrailers(@Path("id") int id, @Query("api_key") String apiKey, @Query("language") String lang);

    @GET("search/movie")
    Observable<MovieResponse> getSearchableMovies(@Query("api_key") String apiKey, @Query("language") String lang,@Query("query") String query);
}
