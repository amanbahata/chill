package com.chillbox.app.anticipatedmovies;

import com.chillbox.app.network.IDataManager;
import com.chillbox.app.network.model.movies.anticipated_movies.PosterLink;
import com.chillbox.app.network.services.Api_List;
import com.chillbox.app.view.base.BasePresenter;
import com.chillbox.app.view.utils.rx.SchedulerProvider;

import java.util.concurrent.TimeUnit;

import javax.inject.Inject;
import javax.inject.Named;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Consumer;

/**
 *  Anticipated Presenter provides the poster of the anticipated movies
 * @param <V> a view
 *
 * @author Aman Bahata
 */

public class MoviePosterPresenter <V extends IAnticipatedMvpView> extends BasePresenter<V>
        implements IAnticipatedMvpPresenter<V> {

    /**
     * Constructor
     * @param dataManager methods to preform network call
     * @param schedulerProvider android scheduler
     * @param compositeDisposable a composite disposable
     */

    @Inject
    public MoviePosterPresenter(@Named("tmdb") IDataManager dataManager, SchedulerProvider schedulerProvider,CompositeDisposable compositeDisposable) {
        super(dataManager, schedulerProvider, compositeDisposable);
    }

    @Override
    public void onCallAnticipatedMoviesList() {
        // does nothing
    }


    /**
     * Gets all the posters for the movies form the api
     */
    @Override
    public void onCallGetMoviePoster(String id) {
        getCompositeDisposable()
                .add(getDataManager().getPosterLink(id, Api_List.TMDB_API_KEY).delay(1000, TimeUnit.MILLISECONDS)
                        .observeOn(getSchedulerProvider().ui())
                        .subscribeOn(getSchedulerProvider().io())
                        .subscribe(new Consumer<PosterLink>() {
                                       @Override
                                       public void accept(PosterLink posterLink) throws Exception {
                                           getMvpView().onFetchPosterSuccess(posterLink);
                                       }
                                   },
                                new Consumer<Throwable>() {
                                    @Override
                                    public void accept(Throwable throwable) throws Exception {
                                        getMvpView().onFetchDataError(throwable.getMessage());
                                    }
                                }

                        )
                );
    }

}
