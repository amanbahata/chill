package com.chillbox.app.movies.mostplayedmovies;

import com.chillbox.app.view.base.MvpPresenter;

/**
 * Created by aman1 on 19/12/2017.
 */

public interface IMostPlayedMoviesMvpPresenter<V extends IMostPlayedMoviesMvpView> extends MvpPresenter<V> {

        void onCallMostPlayedMoviesList();
        void onCallGetMoviePoster(String id);

}
