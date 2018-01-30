package com.chillbox.app.anticipatedmovies;

import com.chillbox.app.view.base.MvpPresenter;

/**
 * Created by aman1 on 14/12/2017.
 */

public interface IAnticipatedMvpPresenter<V extends IAnticipatedMvpView> extends MvpPresenter<V> {
    void onCallAnticipatedMoviesList();
    void onCallGetMoviePoster(String id);
}
