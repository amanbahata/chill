package com.chillbox.app.anticipatedmovies.anticipatedmoviesdetail;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.chillbox.app.R;
import com.chillbox.app.network.services.Api_List;
import com.facebook.drawee.view.SimpleDraweeView;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Fragment for displaying the detail information of an anticipated movie
 *
 * @author Aman Bahata
 * @version 2018/01/10
 */
public class AnticipateDetailFragment extends Fragment {

    /**
     * Debug Tag for use logging debug output to LogCat
     */
    private static final String DEBUG_TAG = "AnticipateDetailFragmentDebugTag";

    @BindView(R.id.detail_title)  TextView mMovieTitle;
    @BindView(R.id.movie_release_date)  TextView mMovieReleaseDate;
    @BindView(R.id.movie_overview)  TextView mMovieOverview;
    @BindView(R.id.movie_detail_artwork)  SimpleDraweeView mDrawee;  // movie poster

    private View mRootView;
    private final String imageUrl = Api_List.IMAGE_URL;  // image url for tmdb


    public AnticipateDetailFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (container != null) {
            container.clearDisappearingChildren();
        }
        if (mRootView == null) {
            mRootView = inflater.inflate(R.layout.fragment_anticipate_detail, container, false);
        }
        ButterKnife.bind(this, mRootView);  // inject fields
        return mRootView;
    }


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Bundle bundle = this.getArguments();


        if (bundle != null) {

            // Unpack all the movie information
            String title = bundle.getString("TITLE");
            String year = bundle.getString("YEAR");
            String overview = bundle.getString("OVERVIEW");
            String posterPath = bundle.getString("POSTER");

            mMovieTitle.setText(title);
            mMovieReleaseDate.setText(getString(R.string.release_year) +" "+ year);

            assert overview != null;
            if(overview.isEmpty()){
                overview = getString(R.string.overview_unavailable);

            }
            mMovieOverview.setText(getString(R.string.overview) +" "+ overview);
            if (posterPath != null){
                mDrawee.setImageURI( imageUrl + posterPath);
            }else{
//                mDrawee.setImageResource(R.drawable.image_not_available);
            }
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}
