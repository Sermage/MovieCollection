package com.sermage.mymoviecollection.data;


import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;



@Entity(tableName = "movies")
public class Movie {
    @PrimaryKey(autoGenerate = true)
    private int uniqueId;
    private  int id;
    private String title;
    private String originalTitle;
    private String releaseDate;
    private String overview;
    private String posterPath;
    private String bigPosterPath;
    private double voteAverage;
    private int voteCount;
    private String backdropPath;

    public Movie(int uniqueId, int id, String title, String originalTitle, String releaseDate, String overview, String posterPath, String bigPosterPath, double voteAverage, int voteCount, String backdropPath) {
        this.uniqueId=uniqueId;
        this.id = id;
        this.title = title;
        this.originalTitle = originalTitle;
        this.releaseDate = releaseDate;
        this.overview = overview;
        this.posterPath = posterPath;
        this.bigPosterPath = bigPosterPath;
        this.voteAverage = voteAverage;
        this.voteCount = voteCount;
        this.backdropPath = backdropPath;
    }
    @Ignore
    public Movie(int id, String title, String originalTitle, String releaseDate, String overview, String posterPath, String bigPosterPath, double voteAverage, int voteCount, String backdropPath) {
        this.id = id;
        this.title = title;
        this.originalTitle = originalTitle;
        this.releaseDate = releaseDate;
        this.overview = overview;
        this.posterPath = posterPath;
        this.bigPosterPath = bigPosterPath;
        this.voteAverage = voteAverage;
        this.voteCount = voteCount;
        this.backdropPath = backdropPath;
    }

    public int getUniqueId() {
        return uniqueId;
    }

    public void setUniqueId(int uniqueId) {
        this.uniqueId = uniqueId;
    }

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getOriginalTitle() {
        return originalTitle;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    public String getOverview() {
        return overview;
    }

    public String getPosterPath() {
        return posterPath;
    }

    public String getBigPosterPath() {
        return bigPosterPath;
    }

    public double getVoteAverage() {
        return voteAverage;
    }

    public int getVoteCount() {
        return voteCount;
    }

    public String getBackdropPath() {
        return backdropPath;
    }

    public void setId (int id) {
        this.id = id;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setOriginalTitle(String originalTitle) {
        this.originalTitle = originalTitle;
    }

    public void setReleaseDate(String releaseDate) {
        this.releaseDate = releaseDate;
    }

    public void setOverview(String overview) {
        this.overview = overview;
    }

    public void setPosterPath(String posterPath) {
        this.posterPath = posterPath;
    }

    public void setBigPosterPath(String bigPosterPath) {
        this.bigPosterPath = bigPosterPath;
    }

    public void setVoteAverage(double voteAverage) {
        this.voteAverage = voteAverage;
    }

    public void setVoteCount(int voteCount) {
        this.voteCount = voteCount;
    }

    public void setBackdropPath(String backdropPath) {
        this.backdropPath = backdropPath;
    }
}
