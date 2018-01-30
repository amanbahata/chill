package com.chillbox.app.movies.mostplayedmovies;


import com.chillbox.app.network.IDataManager;
import com.chillbox.app.network.model.movies.mostplayed_movies.MostPlayedMovies;
import com.chillbox.app.view.base.BasePresenter;
import com.chillbox.app.view.utils.rx.SchedulerProvider;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Consumer;

/**
 * Created by aman1 on 19/12/2017.
 */

public class MostPlayedMoviesPresenter <V extends IMostPlayedMoviesMvpView> extends BasePresenter<V>
        implements IMostPlayedMoviesMvpPresenter<V> {

    @Inject
    public MostPlayedMoviesPresenter(@Named("trackt") IDataManager dataManager, SchedulerProvider schedulerProvider, CompositeDisposable compositeDisposable) {
        super(dataManager, schedulerProvider, compositeDisposable);
    }

    @Override
    public void onCallMostPlayedMoviesList() {
        getCompositeDisposable()
                .add(getDataManager().getMostPlayedMovies()
                        .observeOn(getSchedulerProvider().ui())
                        .subscribeOn(getSchedulerProvider().io())
                        .subscribe(new Consumer<List<MostPlayedMovies>>() {
                                       @Override
                                       public void accept(List<MostPlayedMovies> mostPlayedMovies) throws Exception {
                                           getMvpView().onFetchDataSuccess(mostPlayedMovies);
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

    @Override
    public void onCallGetMoviePoster(String id) {

    }
}
