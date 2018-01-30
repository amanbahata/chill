package com.chillbox.app.anticipatedmovies;


import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.chillbox.app.MainActivity;
import com.chillbox.app.R;
import com.chillbox.app.network.database.realm.RealmAnticipatedMovie;
import com.chillbox.app.network.database.realm.RealmController;
import com.chillbox.app.network.model.movies.anticipated_movies.AnticipatedMoviesWrapper;
import com.chillbox.app.network.model.movies.anticipated_movies.PosterLink;
import com.chillbox.app.injection.components.DaggerAnticipatedComponent;
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
 * Fragment for displaying the most anticipated movies
 *
 * @author Aman Bahata
 * @version 2018/01/10
 */
public class AnticipatedFragment extends Fragment implements IAnticipatedMvpView {

    /**
     * Debug Tag for use logging debug output to LogCat
     */
    private static final String DEBUG_TAG = "AnticipatedFragmentDebugTag";

    @BindView(R.id.anticipated_recyclerview) RecyclerView mAnticipatedRecyclerView;

    @Inject protected AnticipatedPresenter<AnticipatedFragment> anticipatedMoviesPresenter;
    @Inject protected MoviePosterPresenter<AnticipatedFragment> moviePosterPresenter;

    private List<AnticipatedMoviesWrapper> mMovieList;
    private List<PosterLink> posterLink = new ArrayList<>();
    private ArrayList<RealmAnticipatedMovie> realmAnticipatedMovieList;

    private RealmController realmController;
    private View mRootView;
    private Unbinder unbinder;
    private ProgressDialog mProgressDialog;

    public AnticipatedFragment() {
        // Required empty public constructor
    }

    /**
     *Get an instance of the fragment
     * @return an instance of AnticipatedFragment
     */
    public static AnticipatedFragment newInstance() {

        Bundle args = new Bundle();
        AnticipatedFragment fragment = new AnticipatedFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);

        ActionBar actionBar = ((MainActivity) getActivity()).getSupportActionBar();
        if (actionBar != null) {
            actionBar.setTitle(getString(R.string.anticipated_title));
    }

        DaggerAnticipatedComponent.builder()
                .presenterModule(new PresenterModule(this))
                .build()
                .inject(this);

        /* Attach presenters */
        anticipatedMoviesPresenter.onAttach(this);
        moviePosterPresenter.onAttach(this);
        realmController = new RealmController(Realm.getDefaultInstance());  // initialise realm
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        if (container != null) {
            container.clearDisappearingChildren();
        }
        if (mRootView == null) {
            mRootView = inflater.inflate(R.layout.fragment_anticipated, container, false);
            unbinder = ButterKnife.bind(this, mRootView);

            /* Check that network connection is available.
             * if is available submit request
             * otherwise fetch the info from the local database
             */
            if (!isDataConnectionAvailable()){
                fetchFromRealm();
                initialiseRecyclerView();
            }else {
                anticipatedMoviesPresenter.onCallAnticipatedMoviesList();
                mProgressDialog = new ProgressDialog(getContext(),R.style.MyTheme);
                mProgressDialog.setCancelable(true);
                mProgressDialog.setProgressStyle(android.R.style.Widget_ProgressBar_Small);
                mProgressDialog.show();
            }
        }
        return mRootView;
    }


    /**
     * Initialises the RecyclerView and sets app the adaptor ans the layout
     */
    public void initialiseRecyclerView() {
        mAnticipatedRecyclerView.setAdapter(new AnticipatedViewAdaptor(posterLink, R.layout.list_item_anticipated, getActivity()));
        mAnticipatedRecyclerView.setLayoutManager(new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL));
    }

    @Override
    public void onFetchDataSuccess(List<AnticipatedMoviesWrapper> moviesModel) {
        for (AnticipatedMoviesWrapper movie : moviesModel) {
            moviePosterPresenter.onCallGetMoviePoster(movie.getMovie().getIds().getTmdb().toString());
        }
        this.mMovieList = moviesModel;
    }

    @Override
    public void onFetchPosterSuccess(PosterLink posterLink) {
        this.posterLink.add(posterLink);
        storeToRealm(posterLink);

        if (getSizes()) {
            mProgressDialog.dismiss();
            initialiseRecyclerView();
        }
    }
    @Override
    public void onFetchDataError(String message) {
        Log.i("DEBUG_TAG", message);
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
        super.onDestroy();
        if (unbinder != null) {
            unbinder.unbind();
            unbinder = null;
        }
    }


    /**
     * stores a single movie information to a local database
     * @param posterLink the movie information fetched from the link
     */
    public void storeToRealm(PosterLink posterLink){
        Integer id = posterLink.getId();
        String overview = posterLink.getOverview();
        Double popularity = posterLink.getPopularity();
        String posterPath = posterLink.getPosterPath();
        String releaseDate = posterLink.getReleaseDate();
        Integer runtime = posterLink.getRuntime();
        String title = posterLink.getTitle();
        Boolean video = posterLink.getVideo();
        Double voteAverage = posterLink.getVoteAverage();

        RealmAnticipatedMovie realmAnticipatedMovie = new RealmAnticipatedMovie(id,overview, popularity, posterPath, releaseDate,
                runtime, title, video, voteAverage);
        realmController.saveAnticipatedMovie(realmAnticipatedMovie);
    }

    /**
     * Fetches all the movie information stored in the local database
     */
    public void fetchFromRealm(){
        realmAnticipatedMovieList = realmController.getAnticipatedMovies();

        for (RealmAnticipatedMovie movie : realmAnticipatedMovieList) {
            PosterLink link = new PosterLink();
            link.setOverview(movie.getOverview());
            link.setPopularity(movie.getPopularity());
            link.setPosterPath(movie.getPosterPath());
            link.setReleaseDate(movie.getReleaseDate());
            link.setRuntime(movie.getRuntime());
            link.setTitle(movie.getTitle());
            link.setVideo(movie.getVideo());
            link.setVoteAverage(movie.getVoteAverage());

            this.posterLink.add(link);
        }
    }


    /**
     * Gets the number of movies fetched fom the repository
     * @return the size of the movies scontainer
     */
    public boolean getSizes() {
        return posterLink.size() == mMovieList.size();
    }



}
