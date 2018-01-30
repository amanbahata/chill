package com.chillbox.app.movies.moviedetail;

import com.chillbox.app.network.IDataManager;
import com.chillbox.app.network.model.movies.movie_detail.MovieWrapper;
import com.chillbox.app.view.base.BasePresenter;
import com.chillbox.app.view.utils.rx.SchedulerProvider;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Consumer;

/**
 * Created by aman1 on 20/12/2017.
 */

public class MovieDetailPresenter <V extends IMovieDetailMvpView> extends BasePresenter<V>
       implements IMovieDetailMvpPresenter<V>{

    @Inject
    public MovieDetailPresenter(@Named("trackt") IDataManager dataManager, SchedulerProvider schedulerProvider, CompositeDisposable compositeDisposable) {
        super(dataManager, schedulerProvider, compositeDisposable);
    }


    @Override
    public void onCallMovieDetail(String movieId) {
        getCompositeDisposable()
                .add(getDataManager().getMovieDetail(movieId)
                        .observeOn(getSchedulerProvider().ui())
                        .subscribeOn(getSchedulerProvider().io())
                        .subscribe(new Consumer<List<MovieWrapper>>() {
                            @Override
                            public void accept(List<MovieWrapper> movieWrappers) throws Exception {
                                getMvpView().onFetchDataSuccess(movieWrappers);

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
}
