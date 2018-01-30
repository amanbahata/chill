package com.chillbox.app.movies.moviedetail;

import android.support.annotation.NonNull;

import com.chillbox.app.TestSchedulerProvider;
import com.chillbox.app.network.IDataManager;
import com.chillbox.app.network.model.movies.movie_detail.MovieWrapper;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.LinkedList;
import java.util.List;
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
public class MovieDetailPresenterTest {

    private static final String TEST_ERROR = "error";

    @Mock private MovieDetailFragment view;
    @Mock private IDataManager dataManager;
    @Mock private Consumer<List<MovieWrapper>> successConsumer;
    @Mock private Consumer<Throwable> failConsumer;

    private MovieDetailPresenter<MovieDetailFragment> presenter;

    private String movieId = "movieId";

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

//        when(dateHelper.convertTime(anyString())).thenReturn("converted-date");

        presenter = new MovieDetailPresenter<>(dataManager, new TestSchedulerProvider(), new CompositeDisposable());
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
    public void test_onCallMovieDetail_Success() throws Exception {


        List<MovieWrapper> item = new LinkedList<>();

        Observable<List<MovieWrapper>> observable = Observable.just(item);
        observable.subscribe(successConsumer, failConsumer);
        when(this.dataManager.getMovieDetail(movieId)).thenReturn(observable);

        presenter.onCallMovieDetail(movieId);

        verify(successConsumer, atLeastOnce()).accept(eq(item));
        verify(failConsumer, never()).accept(any(Throwable.class));
        verify(view, times(1)).onFetchDataSuccess(eq(item));
    }

    @Test
    public void test_onCallTrendingNewsList_Failure() throws Exception {
        Throwable exception = new Throwable(TEST_ERROR);
        Observable<List<MovieWrapper>> observable = Observable.error(exception);
        observable.subscribe(successConsumer, failConsumer);
        when(this.dataManager.getMovieDetail(movieId)).thenReturn(observable);

        presenter.onCallMovieDetail(movieId);

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