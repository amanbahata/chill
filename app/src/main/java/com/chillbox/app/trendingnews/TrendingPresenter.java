package com.chillbox.app.trendingnews;

import android.support.annotation.Nullable;

import com.chillbox.app.network.IDataManager;
import com.chillbox.app.network.model.trending_news.TrendingArticle;
import com.chillbox.app.network.model.trending_news.TrendingNewsWrapper;
import com.chillbox.app.helper.DateHelper;
import com.chillbox.app.view.base.BasePresenter;
import com.chillbox.app.view.utils.rx.SchedulerProvider;

import java.text.ParseException;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Consumer;

/**
 *  Trending Presenter provides the list of trending news
 * @param <V> a view
 *
 * @author Aman Bahata
 */

public class TrendingPresenter <V extends ITrendingMvpView> extends BasePresenter<V>
        implements ITrendingMvpPresenter<V> {

    private DateHelper dateHelper;

    @Inject
    public TrendingPresenter(@Named("ew") IDataManager dataManager, SchedulerProvider schedulerProvider, CompositeDisposable compositeDisposable, DateHelper dateHelper) {
        super(dataManager, schedulerProvider, compositeDisposable);
        this.dateHelper = dateHelper;
    }

    @Override
    public void onCallTrendingNewsList() {
        getCompositeDisposable()
                .add(getDataManager().getTrendingNews()
                        .observeOn(getSchedulerProvider().ui())
                        .subscribeOn(getSchedulerProvider().io())
                        .subscribe(new Consumer<TrendingNewsWrapper>() {
                                       @Override
                                       public void accept(TrendingNewsWrapper trendingNewsWrapper) throws Exception {
                                           List<TrendingArticle> trendingArticles = mapTrendingArticles(trendingNewsWrapper);
                                           getMvpView().onFetchDataSuccess(trendingArticles);
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


    @Nullable
    private List<TrendingArticle> mapTrendingArticles(TrendingNewsWrapper trendingNewsWrapper) throws ParseException {
        List<TrendingArticle> articles = trendingNewsWrapper.getArticles();
        if (articles != null) {
            for (TrendingArticle article: articles) {
                String articleTitle = article.getTitle();
                if (articleTitle.contains("<em>") && articleTitle.contains("</em>")  ){
                    String newTitle =  articleTitle.replace("<em>", "").replace("</em>", "");
                    article.setTitle(newTitle);
                }
                article.setPublishedAt(this.dateHelper.convertTime(article.getPublishedAt()));
            }
        }
        return articles;
    }
}

