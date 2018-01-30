package com.chillbox.app.movies.mostplayedmovies;


import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.chillbox.app.MainActivity;
import com.chillbox.app.R;
import com.chillbox.app.network.database.realm.RealmController;
import com.chillbox.app.network.database.realm.RealmMostPlayedMovies;
import com.chillbox.app.network.model.movies.mostplayed_movies.MostPlayedMovies;
import com.chillbox.app.network.model.movies.mostplayed_movies.PosterWrapper;
import com.chillbox.app.injection.components.DaggerMostPlayedMoviesComponent;
import com.chillbox.app.injection.modules.PresenterModule;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import io.realm.Realm;

import static com.chillbox.app.MyApp.isDataConnectionAvailable;

/**
 * A simple {@link Fragment} subclass.
 */
public class MostPlayedMoviesFragment extends Fragment implements IMostPlayedMoviesMvpView {

    @BindView(R.id.movies_recyclerview) RecyclerView mMostPlayedRecyclerView;

    @Inject protected MostPlayedMoviesPresenter<MostPlayedMoviesFragment> mMostPlayedMoviesPresenter;
    @Inject protected MoviePosterPresenter<MostPlayedMoviesFragment> moviePosterPresenter;

    private List<PosterWrapper> posterLink = new ArrayList<>();
    private List<MostPlayedMovies> mMovieList;
    private ArrayList<RealmMostPlayedMovies> realmMostPlayedMovies;

    private RealmController realmController;
    private View mRootView;
    private Unbinder unbinder;
    private ProgressDialog mProgressDialog;


    public MostPlayedMoviesFragment() {
        // Required empty public constructor
    }

    public static MostPlayedMoviesFragment newInstance() {

        Bundle args = new Bundle();

        MostPlayedMoviesFragment fragment = new MostPlayedMoviesFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
        ActionBar actionBar = ((MainActivity) getActivity()).getSupportActionBar();
        if (actionBar != null) {
            actionBar.setTitle(getString(R.string.movies_title));
        }

        DaggerMostPlayedMoviesComponent.builder()
                .presenterModule(new PresenterModule(this))
                .build()
                .inject(this);

        moviePosterPresenter.onAttach(this);
        mMostPlayedMoviesPresenter.onAttach(this);
        realmController = new RealmController(Realm.getDefaultInstance());
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        if (container != null) {
            container.clearDisappearingChildren();
        }
        if (mRootView == null) {
            mRootView = inflater.inflate(R.layout.fragment_movies, container, false);
            unbinder = ButterKnife.bind(this, mRootView);
            if (!isDataConnectionAvailable()){
                fetchFromRealm();
                initialiseRecyclerView(getView());
            }else {
                mMostPlayedMoviesPresenter.onCallMostPlayedMoviesList();
                mProgressDialog = new ProgressDialog(getContext(),R.style.MyTheme);
                mProgressDialog.setCancelable(true);
                mProgressDialog.setProgressStyle(android.R.style.Widget_ProgressBar_Small);
                mProgressDialog.show();
            }
        }
        return mRootView;
    }


    public void initialiseRecyclerView(View view) {
        mMostPlayedRecyclerView.setAdapter(new MoviesViewAdaptor(posterLink, R.layout.list_item_movie, getActivity()));
        mMostPlayedRecyclerView.setLayoutManager(new StaggeredGridLayoutManager(2, LinearLayoutManager.VERTICAL));
    }



    @Override
    public void onFetchDataSuccess(List<MostPlayedMovies> mostPlayedMoviesModel) {
        for (MostPlayedMovies movie : mostPlayedMoviesModel) {
            String movieId = movie.getMovie().getIds().getTmdb().toString();
            moviePosterPresenter.onCallGetMoviePoster(movieId);
        }
        this.mMovieList = mostPlayedMoviesModel;
    }


    @Override
    public void onFetchPosterSuccess(PosterWrapper posterLink) {
        this.posterLink.add(posterLink);
        storeToRealm(posterLink);
        if (getSizes()) {
            mProgressDialog.dismiss();
            initialiseRecyclerView(getView());
        }

    }


    @Override
    public void onFetchDataError(String message) {
        Toast.makeText(getContext(), getString(R.string.error_loading_info), Toast.LENGTH_LONG).show();
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
        mMostPlayedMoviesPresenter.onDetach();
        moviePosterPresenter.onDetach();

        if (unbinder != null) {
            unbinder.unbind();
            unbinder = null;
        }
        super.onDestroy();
    }


    public void storeToRealm(PosterWrapper posterLink){

        Integer id = posterLink.getId();
        String overview = posterLink.getOverview();
        Double popularity = posterLink.getPopularity();
        String posterPath = posterLink.getPosterPath();
        String releaseDate = posterLink.getReleaseDate();
        Integer runtime = posterLink.getRuntime();
        String title = posterLink.getTitle();
        Boolean video = posterLink.getVideo();
        Double voteAverage = posterLink.getVoteAverage();

        RealmMostPlayedMovies mostPlayedMovies = new RealmMostPlayedMovies(id, overview, popularity, posterPath, releaseDate,
                runtime, title, video, voteAverage);
        realmController.saveMostPlayedMovie(mostPlayedMovies);

    }

    public void fetchFromRealm(){
        realmMostPlayedMovies = realmController.getMostPlayedMovies();

        for (RealmMostPlayedMovies movie : realmMostPlayedMovies) {
            PosterWrapper link = new PosterWrapper();
            link.setTitle(movie.getTitle());
            link.setReleaseDate(movie.getReleaseDate());
            link.setVoteAverage(movie.getVoteAverage());
            link.setPosterPath(movie.getPosterPath());

            this.posterLink.add(link);
        }
    }

    public boolean getSizes() {
        return posterLink.size() == mMovieList.size();
    }
}
