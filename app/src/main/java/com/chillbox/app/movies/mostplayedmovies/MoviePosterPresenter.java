package com.chillbox.app.movies.mostplayedmovies;


import com.chillbox.app.network.IDataManager;
import com.chillbox.app.network.model.movies.mostplayed_movies.PosterWrapper;
import com.chillbox.app.network.services.Api_List;
import com.chillbox.app.view.base.BasePresenter;
import com.chillbox.app.view.utils.rx.SchedulerProvider;

import javax.inject.Inject;
import javax.inject.Named;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Consumer;

/**
 * Created by aman1 on 17/12/2017.
 */

public class MoviePosterPresenter<V extends IMostPlayedMoviesMvpView> extends BasePresenter<V>
        implements IMostPlayedMoviesMvpPresenter<V> {

    @Inject
    public MoviePosterPresenter(@Named("tmdb") IDataManager dataManager, SchedulerProvider schedulerProvider,
                                CompositeDisposable compositeDisposable) {
        super(dataManager, schedulerProvider, compositeDisposable);
    }



    @Override
    public void onCallGetMoviePoster(String id) {
        getCompositeDisposable()
                .add(getDataManager().getPosterLinkWithAverage(id, Api_List.TMDB_API_KEY)
                        .observeOn(getSchedulerProvider().ui())
                        .subscribeOn(getSchedulerProvider().io())
                        .subscribe(new Consumer<PosterWrapper>() {
                                       @Override
                                       public void accept(PosterWrapper posterWrapper) throws Exception {
                                           getMvpView().onFetchPosterSuccess(posterWrapper);
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
    public void onCallMostPlayedMoviesList() {

    }
}
