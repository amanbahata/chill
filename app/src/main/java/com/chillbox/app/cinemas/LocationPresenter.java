package com.chillbox.app.cinemas;


import com.chillbox.app.network.IDataManager;
import com.chillbox.app.network.model.cinema.CinemaWrapper;
import com.chillbox.app.view.base.BasePresenter;
import com.chillbox.app.view.utils.rx.SchedulerProvider;

import javax.inject.Inject;
import javax.inject.Named;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Consumer;

/**
 * Created by aman1 on 22/12/2017.
 */

public class LocationPresenter <V extends ILocationMvpView> extends BasePresenter<V>
        implements ILocationMvpPresenter<V> {

    @Inject
    public LocationPresenter(@Named("google places") IDataManager dataManager, SchedulerProvider schedulerProvider, CompositeDisposable compositeDisposable) {
        super(dataManager, schedulerProvider, compositeDisposable);
    }

    @Override
    public void onCallCinemaList(String latitude, String longitude, String radius, String type, String key ) {
        getCompositeDisposable()
                .add(getDataManager().getCinemas(latitude + "," + longitude, radius, type, key )
                .observeOn(getSchedulerProvider().ui())
                .subscribeOn(getSchedulerProvider().io())
                .subscribe(new Consumer<CinemaWrapper>() {
                               @Override
                               public void accept(CinemaWrapper cinemaWrapper) throws Exception {
                                   getMvpView().onFetchCinemaListSuccess(cinemaWrapper);
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
