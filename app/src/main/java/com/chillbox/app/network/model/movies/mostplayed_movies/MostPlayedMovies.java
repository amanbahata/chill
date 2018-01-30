package com.chillbox.app.network.model.movies.mostplayed_movies;


import com.chillbox.app.network.model.movies.anticipated_movies.Movie;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by aman1 on 19/12/2017.
 */

public class MostPlayedMovies {

    @SerializedName("watcher_count")
    @Expose
    private Integer watcherCount;
    @SerializedName("play_count")
    @Expose
    private Integer playCount;
    @SerializedName("collected_count")
    @Expose
    private Integer collectedCount;
    @SerializedName("movie")
    @Expose
    private Movie movie;

    public Integer getWatcherCount() {
        return watcherCount;
    }

    public void setWatcherCount(Integer watcherCount) {
        this.watcherCount = watcherCount;
    }

    public Integer getPlayCount() {
        return playCount;
    }

    public void setPlayCount(Integer playCount) {
        this.playCount = playCount;
    }

    public Integer getCollectedCount() {
        return collectedCount;
    }

    public void setCollectedCount(Integer collectedCount) {
        this.collectedCount = collectedCount;
    }

    public Movie getMovie() {
        return movie;
    }

    public void setMovie(Movie movie) {
        this.movie = movie;
    }

}
