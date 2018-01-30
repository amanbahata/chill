package com.chillbox.app.anticipatedmovies;

import com.chillbox.app.network.model.movies.anticipated_movies.AnticipatedMoviesWrapper;
import com.chillbox.app.network.model.movies.anticipated_movies.PosterLink;
import com.chillbox.app.view.base.MvpView;

import java.util.List;

/**
 * Created by aman1 on 19/12/2017.
 */



public interface IAnticipatedMvpView extends MvpView {

    void onFetchDataSuccess(List<AnticipatedMoviesWrapper> moviesModel);
    void onFetchDataError(String message);
    void onFetchPosterSuccess(PosterLink posterLink);

}
