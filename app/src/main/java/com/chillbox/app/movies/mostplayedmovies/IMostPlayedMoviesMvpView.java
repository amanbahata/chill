package com.chillbox.app.movies.mostplayedmovies;

import com.chillbox.app.network.model.movies.mostplayed_movies.MostPlayedMovies;
import com.chillbox.app.network.model.movies.mostplayed_movies.PosterWrapper;
import com.chillbox.app.view.base.MvpView;

import java.util.List;

/**
 * Created by aman1 on 19/12/2017.
 */

public interface IMostPlayedMoviesMvpView extends MvpView{

        void onFetchDataSuccess(List<MostPlayedMovies> mostPlayedMoviesModel);
        void onFetchDataError(String message);
        void onFetchPosterSuccess(PosterWrapper posterLink);

}
