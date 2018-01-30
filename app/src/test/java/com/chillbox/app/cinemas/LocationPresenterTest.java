package com.chillbox.app.cinemas;

import android.support.annotation.NonNull;

import com.chillbox.app.TestSchedulerProvider;
import com.chillbox.app.network.IDataManager;
import com.chillbox.app.network.model.cinema.CinemaWrapper;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.concurrent.Callable;

import io.reactivex.Observable;
import io.reactivex.Scheduler;
import io.reactivex.android.plugins.RxAndroidPlugins;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.plugins.RxJavaPlugins;
import io.reactivex.schedulers.Schedulers;
import io.reactivex.schedulers.TestScheduler;

import static junit.framework.Assert.assertNotNull;
import static junit.framework.Assert.assertNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.atLeastOnce;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Created by aman1 on 04/01/2018.
 */
@RunWith(MockitoJUnitRunner.class)
public class LocationPresenterTest {


    private static final String TEST_ERROR = "error";

    @Mock private LocationFragment view;
    @Mock private IDataManager dataManager;
    @Mock private Consumer<CinemaWrapper> successConsumer;
    @Mock private Consumer<Throwable> failConsumer;

    private LocationPresenter<LocationFragment> presenter;
    private String latitude = "lat", longitude = "lng", radius = "radius", type = "type", key = "key";

    @Before
    public void setUp() throws Exception {
        RxAndroidPlugins.setInitMainThreadSchedulerHandler(new Function<Callable<Scheduler>, Scheduler>() {
            @Override
            public Scheduler apply(@NonNull Callable<Scheduler> schedulerCallable) throws Exception {
                return Schedulers.trampoline();
            }
        });

        RxJavaPlugins.setComputationSchedulerHandler(new Function<Scheduler, Scheduler>() {
            @Override
            public Scheduler apply(@NonNull Scheduler scheduler) throws Exception {
                return new TestScheduler();
            }
        });

        presenter = new LocationPresenter<>(dataManager, new TestSchedulerProvider(), new CompositeDisposable());
        presenter.onAttach(view);
    }


    @Test
    public void testOnAttach(){
        assertNotNull(presenter.getMvpView());
    }

    @Test
    public void testOnDetach(){
        presenter.onDetach();
        assertNull(presenter.getMvpView());
    }

    @Test
    public void test_onCallTrendingNewsList_Success() throws Exception {

        CinemaWrapper item = new CinemaWrapper();

        Observable<CinemaWrapper> observable = Observable.just(item);
        observable.subscribe(successConsumer, failConsumer);
        when(this.dataManager.getCinemas(latitude + "," + longitude, radius, type, key )).thenReturn(observable);

        presenter.onCallCinemaList(latitude, longitude, radius, type, key );

        verify(successConsumer, atLeastOnce()).accept(eq(item));
        verify(failConsumer, never()).accept(any(Throwable.class));
        verify(view, times(1)).onFetchCinemaListSuccess(eq(item));
    }

    @Test
    public void test_onCallTrendingNewsList_Failure() throws Exception {
        Throwable exception = new Throwable(TEST_ERROR);
        Observable<CinemaWrapper> observable = Observable.error(exception);
        observable.subscribe(successConsumer, failConsumer);
        when(this.dataManager.getCinemas(latitude + "," + longitude, radius, type, key )).thenReturn(observable);

        presenter.onCallCinemaList(latitude, longitude, radius, type, key );
        verify(failConsumer).accept(eq(exception));
        verify(successConsumer, never()).accept(any());
        verify(view, times(1)).onFetchDataError(eq(TEST_ERROR));
    }

    @After
    public void tearDown() throws Exception {
        presenter.onDetach();
        RxAndroidPlugins.reset();
        RxJavaPlugins.reset();
    }


}