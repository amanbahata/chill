package com.chillbox.app.anticipatedmovies;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.chillbox.app.MainActivity;
import com.chillbox.app.R;
import com.chillbox.app.anticipatedmovies.anticipatedmoviesdetail.AnticipateDetailFragment;
import com.chillbox.app.network.model.movies.anticipated_movies.PosterLink;
import com.facebook.drawee.drawable.ProgressBarDrawable;
import com.facebook.drawee.view.SimpleDraweeView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Adaptor for the RecyclerView
 * @author Aman Bahata
 */

public class AnticipatedViewAdaptor extends RecyclerView.Adapter<AnticipatedViewAdaptor.ViewHolder> {

    private List<PosterLink> mAnticipatedMovies;
    private int mRowMovie;
    private Context mApplicationContext;
    private final String IMAGE_URL = "https://image.tmdb.org/t/p/w500/";

    public AnticipatedViewAdaptor(List<PosterLink> mAnticipatedMovies, int mRowMovie, Context mApplicationContext) {
        this.mAnticipatedMovies = mAnticipatedMovies;
        this.mRowMovie = mRowMovie;
        this.mApplicationContext = mApplicationContext;

    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(this.mApplicationContext).inflate(mRowMovie, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        String path = mAnticipatedMovies.get(position).getPosterPath();


        holder.mMovieTitle.setText(mAnticipatedMovies.get(position).getTitle());
        holder.mPublishingDate.setText(mAnticipatedMovies.get(position).getReleaseDate());
        holder.bindAnticipatedMovie(mAnticipatedMovies.get(position));

        if (path == null || path.isEmpty()){
//            holder.mDrawee.setImageResource(R.drawable.image_not_available);
        }else{
            holder.mDrawee.setImageURI(IMAGE_URL + path);
        }
    }


    @Override
    public int getItemCount() {
        return mAnticipatedMovies.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        @BindView(R.id.movie_title) TextView mMovieTitle;
        @BindView(R.id.movie_date) TextView mPublishingDate;
        @BindView(R.id.movie_artwork) SimpleDraweeView mDrawee;
        private PosterLink poster;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            mDrawee.getHierarchy().setProgressBarImage(new ProgressBarDrawable());

            itemView.setOnClickListener(this);
        }


        @Override
        public void onClick(View v) {
            if(mApplicationContext instanceof MainActivity){
                Bundle detail = new Bundle();
                detail.putString("TITLE", poster.getTitle());
                detail.putString("YEAR", poster.getReleaseDate());
                detail.putString("OVERVIEW", poster.getOverview());
                detail.putString("HOMEPAGE", (String) poster.getHomepage());
                detail.putString("POSTER", poster.getPosterPath());


                Fragment anticipateDetailFragment = new AnticipateDetailFragment();
                anticipateDetailFragment.setArguments(detail);
                ((MainActivity)mApplicationContext).addFragmentAnticipated(anticipateDetailFragment);
            }
        }

        private void bindAnticipatedMovie(PosterLink poster) {
            this.poster = poster;
        }
    }

}
