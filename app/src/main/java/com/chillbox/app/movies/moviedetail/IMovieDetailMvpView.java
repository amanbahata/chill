package com.chillbox.app.movies.moviedetail;

import com.chillbox.app.network.model.movies.movie_detail.MovieWrapper;
import com.chillbox.app.view.base.MvpView;

import java.util.List;

/**
 * Created by aman1 on 20/12/2017.
 */

public interface IMovieDetailMvpView extends MvpView {

    void onFetchDataSuccess(List<MovieWrapper> moviesModel);
    void onFetchDataError(String message);
}
