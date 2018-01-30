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
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by aman1 on 14/12/2017.
 */

public interface RequestInterface {


    /**
     * Gets the current rending news in the entertainment industry
     * @return
     */

    @GET(Api_List.TRENDING_NEWS_EW)
    Observable<TrendingNewsWrapper> getTrendingNewsList();


    /**
     * Gets the most anticipated upcoming movies
     * @param type
     * @return
     */

    @Headers({
            "Content-type:application/json",
            "trakt-api-version:2",
            "trakt-api-key:" + Api_List.API_KEY_TRAKT
    })
    @GET(Api_List.URL_TRAKT_ANTICIPATED_MOVIES)
    Observable<List<AnticipatedMoviesWrapper>> getAnticipatedMovies(@Path("type")String type);


    /**
     * Gets the most played movies of the month
     * @return
     */

    @Headers({
            "Content-type:application/json",
            "trakt-api-version:2",
            "trakt-api-key:" + Api_List.API_KEY_TRAKT
    })
    @GET(Api_List.URL_MOST_PLAYED_MOVIES_MONTHLY)
    Observable<List<MostPlayedMovies>> getMostPlayedMovies();


    /**
     *
     * @param movieId
     * @return
     */

    @Headers({
            "Content-type:application/json",
            "trakt-api-version:2",
            "trakt-api-key:" + Api_List.API_KEY_TRAKT
    })
    @GET(Api_List.URL_SEARCH_MOVIE)
    Observable<List<MovieWrapper>> getMovieDetail(@Path("id") String movieId);



    /**
     * Gets the poster link for a single anticipated movie from the movie database
     * @param id
     * @param apikey
     * @return
     */
    @GET(Api_List.TMDB_ID)
    Observable<PosterLink> getPosterLink(@Path("id") String id, @Query("api_key")String apikey);

    @GET(Api_List.TMDB_ID)
    Observable<PosterWrapper> getPosterLinkWithAverage(@Path("id") String id, @Query("api_key")String apikey);


    @GET(Api_List.URL_SEARCH_CINEMAS)
    Observable<CinemaWrapper> getCinemas(@Query("location") String latlong,
                                         @Query("radius") String radius,
                                         @Query("type") String type,
                                         @Query("key") String key);

}
