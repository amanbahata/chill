package com.chillbox.app.movies.moviedetail;

import com.chillbox.app.view.base.MvpPresenter;

/**
 * Created by aman1 on 20/12/2017.
 */

public interface IMovieDetailMvpPresenter <V extends IMovieDetailMvpView> extends MvpPresenter<V> {

    void onCallMovieDetail(String movieId);

}
