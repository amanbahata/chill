package com.chillbox.app.trendingnews;

import android.support.annotation.NonNull;

import com.chillbox.app.TestSchedulerProvider;
import com.chillbox.app.network.IDataManager;
import com.chillbox.app.network.model.trending_news.TrendingArticle;
import com.chillbox.app.network.model.trending_news.TrendingNewsWrapper;
import com.chillbox.app.helper.DateHelper;

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
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.atLeastOnce;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Created by aman1 on 30/12/2017.
 */
@RunWith(MockitoJUnitRunner.class)
public class TrendingPresenterTest{
    private static final String TEST_ERROR = "error";
    public static final String TEST_DATE = "2017-12-29T19:04:40Z";

    @Mock private TrendingFragment view;
    @Mock private IDataManager dataManager;
    @Mock private Consumer<TrendingNewsWrapper> successConsumer;
    @Mock private Consumer<Throwable> failConsumer;
    @Mock private DateHelper dateHelper;

    private TrendingPresenter<TrendingFragment> presenter;

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

        when(dateHelper.convertTime(anyString())).thenReturn("converted-date");

        presenter = new TrendingPresenter<>(dataManager, new TestSchedulerProvider(), new CompositeDisposable(), dateHelper);
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
        TrendingNewsWrapper item = new TrendingNewsWrapper();

        List<TrendingArticle> articles = new LinkedList<>();

        TrendingArticle article = new TrendingArticle();
        article.setTitle("<em>Bla bla bla</em>");
        article.setPublishedAt(TEST_DATE);
        articles.add(article);

        item.setArticles(articles);

        Observable<TrendingNewsWrapper> observable = Observable.just(item);
        observable.subscribe(successConsumer, failConsumer);
        when(this.dataManager.getTrendingNews()).thenReturn(observable);

        presenter.onCallTrendingNewsList();

        verify(successConsumer, atLeastOnce()).accept(eq(item));
        verify(failConsumer, never()).accept(any(Throwable.class));
        verify(view, times(1)).onFetchDataSuccess(eq(articles));
    }


    @Test
    public void test_onCallTrendingNewsList_Failure() throws Exception {
        Throwable exception = new Throwable(TEST_ERROR);
        Observable<TrendingNewsWrapper> observable = Observable.error(exception);
        observable.subscribe(successConsumer, failConsumer);
        when(this.dataManager.getTrendingNews()).thenReturn(observable);

        presenter.onCallTrendingNewsList();

        verify(failConsumer).accept(eq(exception));
        verify(successConsumer, never()).accept(any());
        verify(view).onFetchDataError(eq(TEST_ERROR));
    }

    @After
    public void tearDown() throws Exception {
        presenter.onDetach();
        RxAndroidPlugins.reset();
        RxJavaPlugins.reset();
    }
}