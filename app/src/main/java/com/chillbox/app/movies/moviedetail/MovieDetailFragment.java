package com.chillbox.app.movies.moviedetail;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.chillbox.app.MainActivity;
import com.chillbox.app.R;
import com.chillbox.app.network.model.movies.movie_detail.MovieWrapper;
import com.chillbox.app.injection.components.DaggerMovieDetailComponent;
import com.chillbox.app.injection.modules.PresenterModule;
import com.facebook.drawee.view.SimpleDraweeView;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerSupportFragment;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * A simple {@link Fragment} subclass.
 */
public class MovieDetailFragment extends Fragment implements IMovieDetailMvpView {

    private String posterPath;
    private String videoLink;
    private Unbinder unbinder;
    private View mRootView;
    private YouTubePlayer YPlayer;
    private ProgressDialog mProgressDialog;

    @Inject
    protected MovieDetailPresenter<MovieDetailFragment> mMoviePresenter;

    @BindView(R.id.movie_detail_artwork) SimpleDraweeView mDrawee;
    @BindView(R.id.movie_detail_title) TextView mMovieTitle;
    @BindView(R.id.movie_detail_release_year) TextView mReleaseYear;
    @BindView(R.id.movie_detail_runtime) TextView mRunTime;
    @BindView(R.id.movie_detail_rating) TextView mRating;
    @BindView(R.id.movie_detail_overview) TextView mOverview;
    @BindView(R.id.play_video) Button mPlayTrailer;



    public MovieDetailFragment() {
        // Required empty public constructor
    }

    public static MovieDetailFragment newInstance() {
        Bundle args = new Bundle();
        MovieDetailFragment fragment = new MovieDetailFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
        ActionBar actionBar = ((MainActivity) getActivity()).getSupportActionBar();
        if (actionBar != null) {
            actionBar.setTitle(getString(R.string.detail_title));
    }

        DaggerMovieDetailComponent.builder()
                .presenterModule(new PresenterModule(this))
                .build()
                .inject(this);

        mMoviePresenter.onAttach(this);
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (container != null) {
            container.clearDisappearingChildren();
        }
        if (mRootView == null) {
            mRootView = inflater.inflate(R.layout.fragment_movie, container, false);
            unbinder = ButterKnife.bind(this, mRootView);
        }
        return mRootView;
    }



    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Bundle bundle = this.getArguments();

        if (bundle != null) {
            String imdbId = bundle.getString("IMDB_ID");
            posterPath = bundle.getString("POSTER");
            mMoviePresenter.onCallMovieDetail(imdbId);


            mProgressDialog = new ProgressDialog(getContext(),R.style.MyTheme);
            mProgressDialog.setCancelable(true);
            mProgressDialog.setProgressStyle(android.R.style.Widget_ProgressBar_Small);
            mProgressDialog.show();



        mPlayTrailer.setOnClickListener(new Button.OnClickListener() {
            public void onClick(View v) {
                playTrailer(parseVideoLink(videoLink));
            }
        });
        }
    }


    public void playTrailer(String videoLink){

        YouTubePlayerSupportFragment youTubePlayerFragment = YouTubePlayerSupportFragment.newInstance();
        FragmentTransaction transaction = getChildFragmentManager().beginTransaction();
        transaction.add(R.id.youtube_fragment, youTubePlayerFragment).commit();
        youTubePlayerFragment.initialize("AIzaSyBu6Lmo9_Q-OJCx6EgA3L5hI3mWWGsDWC8", new YouTubePlayer.OnInitializedListener() {

            @Override
            public void onInitializationSuccess(YouTubePlayer.Provider arg0, YouTubePlayer youTubePlayer, boolean wasRestored) {
                if (!wasRestored) {
                    YPlayer = youTubePlayer;
                    YPlayer.setFullscreen(true);
                    YPlayer.loadVideo(videoLink);
                    YPlayer.play();
                }
            }

            @Override
            public void onInitializationFailure(YouTubePlayer.Provider arg0, YouTubeInitializationResult arg1) {
                Toast.makeText(getContext(), getString(R.string.video_unavailable), Toast.LENGTH_LONG).show();

            }
        });
    }


    private String parseVideoLink(String videoLink){
        String[] link = videoLink.split("=");
        return link[1];
    }


    @Override
    public void onFetchDataSuccess(List<MovieWrapper> moviesModel) {
        mProgressDialog.dismiss();
        for (MovieWrapper movie : moviesModel) {
            mMovieTitle.setText(movie.getMovie().getTitle());
            mDrawee.setImageURI("https://image.tmdb.org/t/p/w500/" + posterPath);
            videoLink = movie.getMovie().getTrailer();
            mRunTime.setText(getString(R.string.runtime) + movie.getMovie().getRuntime() + getString(R.string.minutes));
            mRating.setText(String.format(getString(R.string.rating) , movie.getMovie().getRating()));
            mOverview.setText(String.format("%s%s", getString(R.string.overview), movie.getMovie().getOverview()));
            mReleaseYear.setText(String.format("%s%s", getString(R.string.release_year), movie.getMovie().getReleased()));
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
        mMoviePresenter.onDetach();
        if (unbinder != null) {
            unbinder.unbind();
            unbinder = null;
        }
        super.onDestroy();
    }

}
