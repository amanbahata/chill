package com.chillbox.app.trendingnews;


import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.chillbox.app.MainActivity;
import com.chillbox.app.R;
import com.chillbox.app.network.model.trending_news.TrendingArticle;
import com.chillbox.app.injection.components.DaggerTrendingComponent;
import com.chillbox.app.injection.modules.PresenterModule;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Fragment that displays the trending entertainment news
 *
 * @author Aman Bahata
 */
public class TrendingFragment extends Fragment implements ITrendingMvpView {

    @BindView(R.id.article_recyclerview) RecyclerView mTrendingRecyclerView;

    @Inject protected TrendingPresenter<ITrendingMvpView> mTrendingNewsPresenter;

    private Unbinder unbinder;
    private View mRootView;
    private ProgressDialog mProgressDialog;

    public TrendingFragment() {
        // Required empty public constructor
    }

    public static TrendingFragment newInstance() {

        Bundle args = new Bundle();

        TrendingFragment fragment = new TrendingFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
        ActionBar actionBar = ((MainActivity) getActivity()).getSupportActionBar();
        if (actionBar != null) {
            actionBar.setTitle(getString(R.string.trending_title));
        }
        DaggerTrendingComponent.builder()
                .presenterModule(new PresenterModule(this))
                .build()
                .inject(this);

        mTrendingNewsPresenter.onAttach(this);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (container != null) {
            container.clearDisappearingChildren();
        }
        if (mRootView == null) {
            mRootView = inflater.inflate(R.layout.fragment_trending, container, false);
            unbinder = ButterKnife.bind(this, mRootView);
            mTrendingNewsPresenter.onCallTrendingNewsList();

            mProgressDialog = new ProgressDialog(getContext(),R.style.MyTheme);
            mProgressDialog.setCancelable(true);
            mProgressDialog.setProgressStyle(android.R.style.Widget_ProgressBar_Small);
            mProgressDialog.show();
        }

        return mRootView;
    }

    @Override
    public void onFetchDataSuccess(List<TrendingArticle> trendingArticles) {
        mProgressDialog.dismiss();
        mTrendingRecyclerView.setAdapter(new TrendingViewAdaptor(trendingArticles, R.layout.list_item_article, getActivity()));
        mTrendingRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
    }


    @Override
    public void onFetchDataError(String message) {
        Toast.makeText(getActivity(), getString(R.string.error_loading_info), Toast.LENGTH_SHORT).show();
    }


    @Override
    public void showLoading() {

    }

    @Override
    public void hideLoading() {

    }

    @Override
    public void openActivityOnTokenExpire() {

    }

    @Override
    public void onError(int resId) {

    }

    @Override
    public void onError(String message) {

    }

    @Override
    public void showMessage(String message) {

    }

    @Override
    public void showMessage(int resId) {

    }

    @Override
    public boolean isNetworkConnected() {
        return false;
    }

    @Override
    public void hideKeyboard() {

    }

    @Override
    public void onDestroy() {
        mTrendingNewsPresenter.onDetach();
        if (unbinder != null) {
            unbinder.unbind();
            unbinder = null;
        }
        super.onDestroy();
    }
}
