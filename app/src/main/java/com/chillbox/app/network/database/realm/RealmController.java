package com.chillbox.app.network.database.realm;

import java.util.ArrayList;

import io.realm.Realm;
import io.realm.RealmResults;

/**
 * Created by aman1 on 27/12/2017.
 */

public class RealmController {

    Realm realm;

    public RealmController(Realm realm) {
        this.realm = realm;
    }


    /**
     * Stores the most played movies list to the database
     * @param realmMostPlayedMovies
     */

    public void saveMostPlayedMovie(RealmMostPlayedMovies realmMostPlayedMovies){
        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                realm.copyToRealmOrUpdate(realmMostPlayedMovies);
            }
        });
    }


    /**
     * Stores the most anticipated movies list to realm
     * @param realmAnticipatedMovie
     */

    public void saveAnticipatedMovie(RealmAnticipatedMovie realmAnticipatedMovie){
        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                realm.copyToRealmOrUpdate(realmAnticipatedMovie);
            }
        });
    }


    /**
     * Fetch the most played movies list from the database
     * @return
     */
    public ArrayList<RealmMostPlayedMovies> getMostPlayedMovies(){

        ArrayList<RealmMostPlayedMovies> moviesList = new ArrayList<>();

        RealmResults<RealmMostPlayedMovies> realmMostPlayedMovieResults = realm.where(RealmMostPlayedMovies.class).findAll();

        for (RealmMostPlayedMovies movie : realmMostPlayedMovieResults) {
            moviesList.add(movie);
        }
        return moviesList;
    }


    /**
     * Fetch the most anticipated movies list from the database
     * @return
     */
    public ArrayList<RealmAnticipatedMovie> getAnticipatedMovies(){

        ArrayList<RealmAnticipatedMovie> moviesList = new ArrayList<>();
        RealmResults<RealmAnticipatedMovie> realmAnticipatedMovieResults = realm.where(RealmAnticipatedMovie.class).findAll();

        for (RealmAnticipatedMovie movie : realmAnticipatedMovieResults) {
            moviesList.add(movie);
        }
        return moviesList;
    }

}
