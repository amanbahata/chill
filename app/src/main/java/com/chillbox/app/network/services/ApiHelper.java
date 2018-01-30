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

public interface ApiHelper {

    Observable<TrendingNewsWrapper> getTrendingNews();
    Observable<List<AnticipatedMoviesWrapper>> getAnticipatedMovies(String type);
    Observable<PosterLink> getPosterLink(String id, String apiKey);
    Observable<PosterWrapper> getPosterLinkWithAverage(String id, String apiKey);
    Observable<List<MostPlayedMovies>> getMostPlayedMovies();
    Observable<List<MovieWrapper>> getMovieDetail(String movieId);

    Observable<CinemaWrapper> getCinemas(String latlong, String radius, String type, String key );

}
