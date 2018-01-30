package com.chillbox.app.network.model.movies.anticipated_movies;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by aman1 on 15/12/2017.
 */

public class Movie {
    @SerializedName("title")
    @Expose
    private String title;
    @SerializedName("year")
    @Expose
    private Integer year;
    @SerializedName("ids")
    @Expose
    private Ids ids;
    @SerializedName("tagline")
    @Expose
    private String tagline;
    @SerializedName("overview")
    @Expose
    private String overview;
    @SerializedName("released")
    @Expose
    private String released;
    @SerializedName("runtime")
    @Expose
    private Integer runtime;
    @SerializedName("trailer")
    @Expose
    private String trailer;
    @SerializedName("homepage")
    @Expose
    private Object homepage;
    @SerializedName("rating")
    @Expose
    private Double rating;
    @SerializedName("votes")
    @Expose
    private Integer votes;
    @SerializedName("comment_count")
    @Expose
    private Integer commentCount;
    @SerializedName("updated_at")
    @Expose
    private String updatedAt;
    @SerializedName("language")
    @Expose
    private String language;
    @SerializedName("available_translations")
    @Expose
    private List<String> availableTranslations = null;
    @SerializedName("genres")
    @Expose
    private List<String> genres = null;
    @SerializedName("certification")
    @Expose
    private String certification;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Integer getYear() {
        return year;
    }

    public void setYear(Integer year) {
        this.year = year;
    }

    public Ids getIds() {
        return ids;
    }

    public void setIds(Ids ids) {
        this.ids = ids;
    }

    public String getTagline() {
        return tagline;
    }

    public void setTagline(String tagline) {
        this.tagline = tagline;
    }

    public String getOverview() {
        return overview;
    }

    public void setOverview(String overview) {
        this.overview = overview;
    }

    public String getReleased() {
        return released;
    }

    public void setReleased(String released) {
        this.released = released;
    }

    public Integer getRuntime() {
        return runtime;
    }

    public void setRuntime(Integer runtime) {
        this.runtime = runtime;
    }

    public String getTrailer() {
        return trailer;
    }

    public void setTrailer(String trailer) {
        this.trailer = trailer;
    }

    public Object getHomepage() {
        return homepage;
    }

    public void setHomepage(Object homepage) {
        this.homepage = homepage;
    }

    public Double getRating() {
        return rating;
    }

    public void setRating(Double rating) {
        this.rating = rating;
    }

    public Integer getVotes() {
        return votes;
    }

    public void setVotes(Integer votes) {
        this.votes = votes;
    }

    public Integer getCommentCount() {
        return commentCount;
    }

    public void setCommentCount(Integer commentCount) {
        this.commentCount = commentCount;
    }

    public String getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public List<String> getAvailableTranslations() {
        return availableTranslations;
    }

    public void setAvailableTranslations(List<String> availableTranslations) {
        this.availableTranslations = availableTranslations;
    }

    public List<String> getGenres() {
        return genres;
    }

    public void setGenres(List<String> genres) {
        this.genres = genres;
    }

    public String getCertification() {
        return certification;
    }

    public void setCertification(String certification) {
        this.certification = certification;
    }


}
