package com.chillbox.app.anticipatedmovies;

import com.chillbox.app.network.IDataManager;
import com.chillbox.app.network.model.movies.anticipated_movies.AnticipatedMoviesWrapper;
import com.chillbox.app.view.base.BasePresenter;
import com.chillbox.app.view.utils.rx.SchedulerProvider;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Consumer;

/**
 *  Anticipated Presenter provides the list of anticipated movies
 * @param <V> a view
 *
 * @author Aman Bahata
 */
public class AnticipatedPresenter <V extends IAnticipatedMvpView> extends BasePresenter<V>
        implements IAnticipatedMvpPresenter<V> {

    /**
     * Constructor
     * @param dataManager methods to preform network call
     * @param schedulerProvider android scheduler
     * @param compositeDisposable a composite disposable
     */

    @Inject
    public AnticipatedPresenter(@Named("trackt") IDataManager dataManager, SchedulerProvider schedulerProvider,
                                CompositeDisposable compositeDisposable) {
        super(dataManager, schedulerProvider, compositeDisposable);

    }


    /**
     * Gets all the upcoming anticipated movies form the api
     */
    @Override
    public void onCallAnticipatedMoviesList() {
        getCompositeDisposable()
                .add(getDataManager().getAnticipatedMovies("movies")
                        .observeOn(getSchedulerProvider().ui())
                        .subscribeOn(getSchedulerProvider().io())
                        .subscribe(new Consumer<List<AnticipatedMoviesWrapper>>() {
                                       @Override
                                       public void accept(List<AnticipatedMoviesWrapper> anticipatedMoviesWrappers) throws Exception {
                                           getMvpView().onFetchDataSuccess(anticipatedMoviesWrappers);
                                       }
                                   },
                                new Consumer<Throwable>() {
                                    @Override
                                    public void accept(Throwable throwable) throws Exception {
                                        getMvpView().onFetchDataError(throwable.getMessage());
                                    }
                                })
                );
    }

    @Override
    public void onCallGetMoviePoster(String id) {
        // does nothing
    }


}
