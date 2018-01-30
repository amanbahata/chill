package com.chillbox.app.network.model.movies.movie_detail;

import com.chillbox.app.network.model.movies.anticipated_movies.Movie;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by aman1 on 20/12/2017.
 */

public class MovieWrapper {

    @SerializedName("type")
    @Expose
    private String type;
    @SerializedName("score")
    @Expose
    private Object score;
    @SerializedName("movie")
    @Expose
    private Movie movie;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Object getScore() {
        return score;
    }

    public void setScore(Object score) {
        this.score = score;
    }

    public Movie getMovie() {
        return movie;
    }

    public void setMovie(Movie movie) {
        this.movie = movie;
    }
}
