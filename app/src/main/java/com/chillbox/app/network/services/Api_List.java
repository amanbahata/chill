package com.chillbox.app.network.services;

/**
 * Hold the list of Api
 */

public class Api_List {

    // Api key for trackt.tv server
    public static final String API_KEY_TRAKT = "2eb8329abd9cb3e9210d7bb1e6ad873a3dac5f9306132526bb7dcf1e91d206c5";
    public static final String BASE_URL_TRAKT = "https://api.trakt.tv/";
    public static final String URL_TRAKT_ANTICIPATED_MOVIES = "{type}/anticipated?&limit=30&extended=full";
    public static final String URL_MOST_PLAYED_MOVIES_MONTHLY = "movies/played/yearly?&limit=40&extended=full";  // limit 40 so we dont go ober the request limit
    public static final String URL_SEARCH_MOVIE = "search/imdb/{id}?extended=full";

    // The movie database movie detail image
    public static final String TMDB_API_KEY = "ab9a472ca3d38fdafd36824601eb6077";
    public static final String BASE_URL_TMDB = "https://api.themoviedb.org/3/";
    public static final String TMDB_ID = "movie/{id}";
    public static final String IMAGE_URL = "https://image.tmdb.org/t/p/w500/";

    // Api for entertainment weekly server
    public static final String BASE_URL_EW = "https://newsapi.org/v2/";
    public static final String TRENDING_NEWS_EW = "everything?sources=entertainment-weekly&apiKey=68a22a90ac0c487884a211246bc00ff9";

    // Api google places for cinemas
    public static final String API_KEY_GOOGLE_PLACES = "AIzaSyBu6Lmo9_Q-OJCx6EgA3L5hI3mWWGsDWC8";
    public static final String BASE_URL_Google_PLACES = "https://maps.googleapis.com/maps/api/";
    public static final String URL_SEARCH_CINEMAS = "place/nearbysearch/json";




}
