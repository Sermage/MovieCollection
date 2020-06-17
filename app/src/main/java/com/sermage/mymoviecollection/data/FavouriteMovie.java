package com.sermage.mymoviecollection.data;

import androidx.room.Entity;
import androidx.room.Ignore;

import com.sermage.mymoviecollection.pojo.Movie;

@Entity(tableName = "favourite_movie")
public class FavouriteMovie extends Movie {
    public FavouriteMovie(int uniqueId,int voteCount, String posterPath, int id, String backdropPath, String originalTitle, String title, double voteAverage, String overview, String releaseDate) {
        super(uniqueId,voteCount, posterPath, id, backdropPath, originalTitle, title, voteAverage, overview, releaseDate);
    }

    @Ignore
    public FavouriteMovie(Movie movie) {
        super(movie.getUniqueId(),movie.getVoteCount(),movie.getPosterPath(),movie.getId(),movie.getBackdropPath(),movie.getOriginalTitle(),movie.getTitle(),movie.getVoteAverage(),movie.getOverview(),movie.getReleaseDate());
    }
}
