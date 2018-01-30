package com.chillbox.app.network.services;


import com.chillbox.app.network.model.cinema.CinemaWrapper;
import com.chillbox.app.network.model.movies.anticipated_movies.AnticipatedMoviesWrapper;
import com.chillbox.app.network.model.movies.anticipated_movies.PosterLink;
import com.chillbox.app.network.model.movies.mostplayed_movies.MostPlayedMovies;
import com.chillbox.app.network.model.movies.mostplayed_movies.PosterWrapper;
import com.chillbox.app.network.model.movies.movie_detail.MovieWrapper;
import com.chillbox.app.network.model.trending_news.TrendingNewsWrapper;

import java.util.List;

import io.reactivex.Observable;

/**
 * Created by aman1 on 24/11/2017.
 */


public class AppApiHelper implements ApiHelper {


    RequestInterface requestInterface;

    public AppApiHelper(String api) {
       requestInterface = ServerConnection.getServerConnection(api);
    }


    @Override
    public Observable<TrendingNewsWrapper> getTrendingNews() {
        return requestInterface.getTrendingNewsList();
    }

    @Override
    public Observable<List<AnticipatedMoviesWrapper>> getAnticipatedMovies(String type) {
        return requestInterface.getAnticipatedMovies(type);
    }

    @Override
    public Observable<PosterLink> getPosterLink(String id, String apikey) {
        return requestInterface.getPosterLink(id,apikey);
    }

    @Override
    public Observable<PosterWrapper> getPosterLinkWithAverage(String id, String apikey) {
        return requestInterface.getPosterLinkWithAverage(id,apikey);
    }

    @Override
    public Observable<List<MostPlayedMovies>> getMostPlayedMovies() {
        return requestInterface.getMostPlayedMovies();
    }

    @Override
    public Observable<List<MovieWrapper>> getMovieDetail(String movieId) {
        return requestInterface.getMovieDetail(movieId);
    }

    @Override
    public Observable<CinemaWrapper> getCinemas(String latlong, String radius, String type, String key ) {
        return requestInterface.getCinemas(latlong, radius, type, key);
    }

}
