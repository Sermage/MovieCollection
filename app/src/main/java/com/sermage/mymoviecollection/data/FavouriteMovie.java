package com.sermage.mymoviecollection.data;

import androidx.room.Entity;
import androidx.room.Ignore;

@Entity(tableName = "favourite_movie")
public class FavouriteMovie extends Movie {
    public FavouriteMovie(int uniqueId, int id, String title, String originalTitle, String releaseDate, String overview, String posterPath,
                          String bigPosterPath, double voteAverage, int voteCount, String backdropPath) {
        super(uniqueId,id, title, originalTitle, releaseDate, overview, posterPath, bigPosterPath, voteAverage, voteCount, backdropPath);
    }
    @Ignore
    public FavouriteMovie(Movie movie) {
        super(movie.getUniqueId(), movie.getId(),movie.getTitle(),movie.getOriginalTitle(),movie.getReleaseDate(),movie.getOverview(),movie.getPosterPath(),
                movie.getBigPosterPath(),movie.getVoteAverage(),movie.getVoteCount(),movie.getBackdropPath());
    }
}
