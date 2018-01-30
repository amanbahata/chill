package com.chillbox.app.network.model.trending_news;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by aman1 on 14/12/2017.
 */

public class TrendingNewsWrapper {

    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("totalResults")
    @Expose
    private Integer totalResults;
    @SerializedName("articles")
    @Expose
    private List<TrendingArticle> articles = null;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Integer getTotalResults() {
        return totalResults;
    }

    public void setTotalResults(Integer totalResults) {
        this.totalResults = totalResults;
    }

    public List<TrendingArticle> getArticles() {
        return articles;
    }

    public void setArticles(List<TrendingArticle> articles) {
        this.articles = articles;
    }
}
