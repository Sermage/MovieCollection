package com.sermage.mymoviecollection.api

import com.sermage.mymoviecollection.pojo.*
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query
import java.util.*

interface ApiService {
    @GET("discover/movie")
   fun getMostRatedMovies(
        @Query(PARAM_KEY) apiKey: String= API_KEY,
        @Query(PARAM_LANG) lang: String= LANG,
        @Query(PARAM_SORT_BY) methodSort: String= SORT_BY_VOTE_AVERAGE,
        @Query(PARAM_VOTE_COUNT) voteCount: String="1000",
        @Query(PAGE) page: Int=1): Single<MovieResponse>

    @GET("discover/movie")
    fun getMovies(
        @Query(PARAM_KEY) apiKey: String= API_KEY,
        @Query(PARAM_LANG) lang: String= LANG,
        @Query(PAGE) page: Int=1,
        @Query(PARAM_CERTIFICATION_COUNTRY) certificationCountry:String= CERTIFICATION_COUNTRY,
        @Query(PARAM_CERTIFICATION) certification:String= CERTIFICATION,
        @Query(PARAM_SORT_BY) sort_by:String= SORT_BY_POPULARITY,
    ):Single<MovieResponse>

    @GET("movie/{id}")
    fun getMovieDetails(
        @Path(PARAM_ID) id:Int,
        @Query(PARAM_KEY) apiKey: String= API_KEY,
        @Query(PARAM_LANG) lang: String= LANG
    ):Single<MovieDetails>

    @GET("discover/tv")
    fun getMostRatedTvShows(
        @Query(PARAM_KEY) apiKey: String= API_KEY,
        @Query(PARAM_LANG) lang: String= LANG,
        @Query(PARAM_SORT_BY) methodSort: String= SORT_BY_VOTE_AVERAGE,
        @Query(PARAM_VOTE_COUNT) voteCount: String="1000",
        @Query(PAGE) page: Int=1): Single<TVShowResponse>

    @GET("movie/{id}/reviews")
    fun getReviews(
            @Path(PARAM_ID) id: Int,
            @Query(PARAM_KEY) apiKey: String= API_KEY): Single<ReviewResponse>

    @GET("movie/{id}/videos")
    fun getTrailers(
            @Path(PARAM_ID) id: Int,
            @Query(PARAM_KEY) apiKey: String= API_KEY,
            @Query(PARAM_LANG) lang: String= LANG): Single<TrailerResponse>

    @GET("search/movie")
     fun getSearchableMovies(
            @Query(PARAM_KEY) apiKey: String= API_KEY,
            @Query(PARAM_LANG) lang: String= LANG,
            @Query(PARAM_QUERY) query: String): Single<MovieResponse>

    @GET("trending/movie/{time_window}")
    fun getTrendingMovies(
        @Path(PARAM_TIME_WINDOW) timeWindow:String= TIME_WINDOW,
        @Query(PARAM_KEY) apiKey: String= API_KEY,
        @Query(PAGE) page: Int=1
    ):Single<MovieResponse>

    @GET("trending/tv/{time_window}")
    fun getTrendingTVShows(
        @Path(PARAM_TIME_WINDOW) timeWindow:String= TIME_WINDOW,
        @Query(PARAM_KEY) apiKey: String= API_KEY,
        @Query(PAGE) page: Int=1
    ):Single<TVShowResponse>

    @GET("tv/{id}/reviews")
    fun getTVShowReviews(
        @Path(PARAM_ID) id: Int,
        @Query(PARAM_KEY) apiKey: String= API_KEY): Single<ReviewResponse>

    @GET("tv/{id}/videos")
    fun getTvShowTrailers(
        @Path(PARAM_ID) id: Int,
        @Query(PARAM_KEY) apiKey: String= API_KEY,
        @Query(PARAM_LANG) lang: String= LANG): Single<TrailerResponse>


    companion object{
        private const val PARAM_KEY="api_key"
        private const val PARAM_ID="id"
        private const val PARAM_LANG="language"
        private const val PARAM_SORT_BY="sort_by"
        private const val SORT_BY_POPULARITY="popularity.desc"
        private const val PARAM_VOTE_COUNT="vote_count.gte"
        private const val PAGE="page"
        private const val PARAM_QUERY="query"
        private val SORT_BY_VOTE_AVERAGE = "vote_average.desc"
        private const val API_KEY="bcd0186eaa110f9203b13f70c974df31"
        private var LANG=Locale.getDefault().language
        private const val PARAM_MEDIA_TYPE="media_type"
        private const val PARAM_TIME_WINDOW="time_window"
        private const val MEDIA_TYPE="movie"
        private const val TIME_WINDOW="week"
        private const val PARAM_CERTIFICATION_COUNTRY="certification_country"
        private const val CERTIFICATION_COUNTRY="RU"
        private const val PARAM_CERTIFICATION="certification"
        private const val CERTIFICATION="0+"

    }
}