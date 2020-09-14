package com.sermage.mymoviecollection.api

import com.sermage.mymoviecollection.pojo.MovieResponse
import com.sermage.mymoviecollection.pojo.ReviewResponse
import com.sermage.mymoviecollection.pojo.TrailerResponse
import io.reactivex.Observable
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query
import java.util.*

interface ApiService {
    @GET("discover/movie")
   fun getMovies(
            @Query(PARAM_KEY) apiKey: String= API_KEY,
            @Query(PARAM_LANG) lang: String= LANG,
            @Query(PARAM_SORT_BY) methodSort: String,
            @Query(PARAM_VOTE_COUNT) voteCount: String="1000",
            @Query(PAGE) page: Int=1): Single<MovieResponse>

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

    companion object{
        private const val PARAM_KEY="api_key"
        private const val PARAM_ID="id"
        private const val PARAM_LANG="language"
        private const val PARAM_SORT_BY="sort_by"
        private const val PARAM_VOTE_COUNT="vote_count.gte"
        private const val PAGE="page"
        private const val PARAM_QUERY="query"
        private const val API_KEY="bcd0186eaa110f9203b13f70c974df31"
        private var LANG=Locale.getDefault().language
    }
}