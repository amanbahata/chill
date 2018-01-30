package com.chillbox.app.network;



import com.chillbox.app.network.model.cinema.CinemaWrapper;
import com.chillbox.app.network.model.movies.anticipated_movies.AnticipatedMoviesWrapper;
import com.chillbox.app.network.model.movies.anticipated_movies.PosterLink;
import com.chillbox.app.network.model.movies.mostplayed_movies.MostPlayedMovies;
import com.chillbox.app.network.model.movies.mostplayed_movies.PosterWrapper;
import com.chillbox.app.network.model.movies.movie_detail.MovieWrapper;
import com.chillbox.app.network.model.trending_news.TrendingNewsWrapper;
import com.chillbox.app.network.services.ApiHelper;
import com.chillbox.app.network.services.AppApiHelper;

import java.util.List;

import io.reactivex.Observable;

/**
 * Created by aman1 on 26/11/2017.
 */

public class AppDataManager implements IDataManager {

    private ApiHelper apiHelper;


    public AppDataManager(String api) {
        apiHelper = new AppApiHelper(api);
    }

    @Override
    public Observable<TrendingNewsWrapper> getTrendingNews() {
        return apiHelper.getTrendingNews();
    }

    @Override
    public Observable<List<AnticipatedMoviesWrapper>> getAnticipatedMovies(String type) {
        return apiHelper.getAnticipatedMovies(type);
    }

    @Override
    public Observable<PosterLink> getPosterLink(String id, String apiKey) {
        return apiHelper.getPosterLink(id, apiKey);
    }

    @Override
    public Observable<PosterWrapper> getPosterLinkWithAverage(String id, String apiKey) {
        return apiHelper.getPosterLinkWithAverage(id, apiKey);
    }

    @Override
    public Observable<List<MostPlayedMovies>> getMostPlayedMovies() {
        return apiHelper.getMostPlayedMovies();
    }

    @Override
    public Observable<List<MovieWrapper>> getMovieDetail(String movieId) {
        return apiHelper.getMovieDetail(movieId);
    }

    @Override
    public Observable<CinemaWrapper> getCinemas(String latlong, String radius, String type, String key ) {
        return apiHelper.getCinemas(latlong, radius, type, key );
    }
}
