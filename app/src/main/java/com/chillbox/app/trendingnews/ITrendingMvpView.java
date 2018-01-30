package com.chillbox.app.trendingnews;

import com.chillbox.app.network.model.trending_news.TrendingArticle;
import com.chillbox.app.view.base.MvpView;

import java.util.List;

/**
 * Created by aman1 on 19/12/2017.
 */

public interface ITrendingMvpView extends MvpView {

    void onFetchDataSuccess(List<TrendingArticle> trendingArticles);
    void onFetchDataError(String message);
}
