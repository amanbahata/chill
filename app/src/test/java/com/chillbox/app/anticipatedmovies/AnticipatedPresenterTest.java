package com.chillbox.app.anticipatedmovies;

import android.support.annotation.NonNull;

import com.chillbox.app.TestSchedulerProvider;
import com.chillbox.app.network.IDataManager;
import com.chillbox.app.network.model.movies.anticipated_movies.AnticipatedMoviesWrapper;

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
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Created by aman1 on 01/01/2018.
 */
@RunWith(MockitoJUnitRunner.class)
public class AnticipatedPresenterTest {

    private static final String TEST_ERROR = "error";
    private static final String TEST_TYPE = "movies";

    @Mock private AnticipatedFragment view;
    @Mock private IDataManager dataManager;
    @Mock private Consumer <List<AnticipatedMoviesWrapper>> successConsumer;
    @Mock private Consumer<Throwable> failConsumer;

    private AnticipatedPresenter<AnticipatedFragment> presenter;
    private Observable<List<AnticipatedMoviesWrapper>> observable;



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

        CompositeDisposable disposable = new CompositeDisposable();
        presenter = new AnticipatedPresenter<>(dataManager, new TestSchedulerProvider(), disposable);
        presenter.onAttach(view);
    }


    @Test
    public void testOnAttach(){
        presenter.onAttach(view);
        assertNotNull(presenter.getMvpView());
    }

    @Test
    public void testOnDetach(){
        presenter.onDetach();
        assertNull(presenter.getMvpView());
    }

    @Test
    public void test_onCallTrendingNewsList_Success() throws Exception {

        List<AnticipatedMoviesWrapper> item = new LinkedList<>();

        observable = Observable.just(item);
        observable.subscribe(successConsumer, failConsumer);
        when(this.dataManager.getAnticipatedMovies(TEST_TYPE)).thenReturn(observable);

        presenter.onCallAnticipatedMoviesList();

        verify(successConsumer, atLeastOnce()).accept(eq(item));
        verify(failConsumer, never()).accept(any(Throwable.class));
        verify(view).onFetchDataSuccess(any());

    }

    @Test
    public void test_onCallTrendingNewsList_Failure() throws Exception {
        Throwable exception = new Throwable(TEST_ERROR);
        observable = Observable.error(exception);
        observable.subscribe(successConsumer, failConsumer);
        when(this.dataManager.getAnticipatedMovies(TEST_TYPE)).thenReturn(observable);

        presenter.onCallAnticipatedMoviesList();

        verify(failConsumer).accept(eq(exception));
        verify(view).onFetchDataError(eq(TEST_ERROR));
    }

    @After
    public void tearDown() throws Exception {
        RxAndroidPlugins.reset();
        RxJavaPlugins.reset();
        presenter.onDetach();
    }
}