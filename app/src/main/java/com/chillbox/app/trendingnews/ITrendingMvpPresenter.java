package com.chillbox.app.trendingnews;


import com.chillbox.app.view.base.MvpPresenter;

/**
 * Created by aman1 on 19/12/2017.
 */

public interface ITrendingMvpPresenter <V extends ITrendingMvpView> extends MvpPresenter<V> {

    void onCallTrendingNewsList();
}
