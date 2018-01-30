package com.chillbox.app.network.database.realm;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by aman1 on 28/12/2017.
 */

public class RealmMostPlayedMovies extends RealmObject {

    @PrimaryKey
    private Integer id;
    private String overview;
    private Double popularity;
    private String posterPath;
    private String releaseDate;
    private Integer runtime;
    private String title;
    private Boolean video;
    private Double voteAverage;

    public RealmMostPlayedMovies() {
    }

    public RealmMostPlayedMovies(Integer id, String overview, Double popularity, String posterPath,
                                 String releaseDate, Integer runtime,
                                 String title, Boolean video, Double voteAverage) {
        this.id = id;
        this.overview = overview;
        this.popularity = popularity;
        this.posterPath = posterPath;
        this.releaseDate = releaseDate;
        this.runtime = runtime;
        this.title = title;
        this.video = video;
        this.voteAverage = voteAverage;
    }



    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getOverview() {
        return overview;
    }

    public void setOverview(String overview) {
        this.overview = overview;
    }

    public Double getPopularity() {
        return popularity;
    }

    public void setPopularity(Double popularity) {
        this.popularity = popularity;
    }

    public String getPosterPath() {
        return posterPath;
    }

    public void setPosterPath(String posterPath) {
        this.posterPath = posterPath;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(String releaseDate) {
        this.releaseDate = releaseDate;
    }

    public Integer getRuntime() {
        return runtime;
    }

    public void setRuntime(Integer runtime) {
        this.runtime = runtime;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Boolean getVideo() {
        return video;
    }

    public void setVideo(Boolean video) {
        this.video = video;
    }

    public Double getVoteAverage() {
        return voteAverage;
    }

    public void setVoteAverage(Double voteAverage) {
        this.voteAverage = voteAverage;
    }
}
