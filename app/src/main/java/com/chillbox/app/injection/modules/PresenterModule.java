package com.chillbox.app.injection.modules;

import com.chillbox.app.network.AppDataManager;
import com.chillbox.app.network.IDataManager;
import com.chillbox.app.network.services.Api_List;
import com.chillbox.app.injection.scope.PerActivity;
import com.chillbox.app.view.base.MvpView;
import com.chillbox.app.view.utils.rx.AppSchedulerProvider;
import com.chillbox.app.view.utils.rx.SchedulerProvider;

import javax.inject.Named;

import dagger.Module;
import dagger.Provides;
import io.reactivex.disposables.CompositeDisposable;

/**
 * Created by aman1 on 02/01/2018.
 */
@Module
public class PresenterModule {
    private final MvpView view;

    public PresenterModule(MvpView view) {
        this.view = view;
    }

    @PerActivity
    @Provides @Named("ew")
    IDataManager provideAppDataManagerEw() {
        return new AppDataManager(Api_List.BASE_URL_EW);
    }

    @PerActivity
    @Provides @Named("trackt")
    IDataManager provideAppDataManagerTrackt() {
        return new AppDataManager(Api_List.BASE_URL_TRAKT);
    }

    @PerActivity
    @Provides @Named("tmdb")
    IDataManager provideAppDataManagerTmdb() {
        return new AppDataManager(Api_List.BASE_URL_TMDB);
    }

    @PerActivity
    @Provides @Named("google places")
    IDataManager provideAppDataManagerGooglePlaces(){
        return new AppDataManager(Api_List.BASE_URL_Google_PLACES);
    }


    @PerActivity
    @Provides
    SchedulerProvider provideAppSchedulerProvider(AppSchedulerProvider provider) {
        return provider;
    }

    @PerActivity
    @Provides
    CompositeDisposable provideCompositeDisposable() {
        return new CompositeDisposable();
    }

    @Provides
    @PerActivity
    MvpView provideView() {
        return view;
    }
}
