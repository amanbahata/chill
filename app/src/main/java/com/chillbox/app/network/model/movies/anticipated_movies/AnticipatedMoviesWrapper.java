package com.chillbox.app.network.model.movies.anticipated_movies;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by aman1 on 15/12/2017.
 */

public class AnticipatedMoviesWrapper {
    @SerializedName("list_count")
    @Expose
    private Integer listCount;
    @SerializedName("movie")
    @Expose
    private Movie movie;

    public Integer getListCount() {
        return listCount;
    }

    public void setListCount(Integer listCount) {
        this.listCount = listCount;
    }

    public Movie getMovie() {
        return movie;
    }

    public void setMovie(Movie movie) {
        this.movie = movie;
    }
}
