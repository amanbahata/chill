package com.chillbox.app.movies.mostplayedmovies;

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
import com.chillbox.app.network.model.movies.mostplayed_movies.PosterWrapper;
import com.chillbox.app.movies.moviedetail.MovieDetailFragment;
import com.facebook.drawee.view.SimpleDraweeView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by aman1 on 19/12/2017.
 */

public class MoviesViewAdaptor extends RecyclerView.Adapter<MoviesViewAdaptor.ViewHolder> {

    private List<PosterWrapper> mMostPlayedMovies;
    private int mRowMovie;
    private Context mApplicationContext;
    private final String IMAGE_URL = "https://image.tmdb.org/t/p/w500/";


    public MoviesViewAdaptor(List<PosterWrapper> mMostPlayedMovies, int mRowMovie, Context mApplicationContext) {
        this.mMostPlayedMovies = mMostPlayedMovies;
        this.mRowMovie = mRowMovie;
        this.mApplicationContext = mApplicationContext;

    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(this.mApplicationContext).inflate(mRowMovie, parent, false);
        return new ViewHolder(view);
    }


        @Override
    public void onBindViewHolder(MoviesViewAdaptor.ViewHolder holder, int position) {
        holder.mMovieTitle.setText(mMostPlayedMovies.get(position).getTitle());
        holder.mPublishingDate.setText(mMostPlayedMovies.get(position).getReleaseDate());
            double rating  = mMostPlayedMovies.get(position).getVoteAverage();
            rating = Math.floor(rating * 10) / 10;
        holder.mMovieRating.setText(rating + "/10");
        holder.mDrawee.setImageURI(IMAGE_URL + mMostPlayedMovies.get(position).getPosterPath());
        holder.bindMostPlayedMovie(mMostPlayedMovies.get(position));
    }

    @Override
    public int getItemCount() {
        return mMostPlayedMovies.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        @BindView(R.id.movie_title) TextView mMovieTitle;
        @BindView(R.id.movie_date) TextView mPublishingDate;
        @BindView(R.id.movie_rating) TextView mMovieRating;
        @BindView(R.id.movie_artwork) SimpleDraweeView mDrawee;
        private PosterWrapper poster;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            Bundle detail = new Bundle();
            detail.putString("IMDB_ID", poster.getImdbId());
            String posterPath;
            if (poster.getPosterPath() != null) {
                posterPath = poster.getPosterPath();
            }else{
                posterPath = "/unavailable";
            }

            detail.putString("POSTER", posterPath);


            Fragment movieDetailFragment = new MovieDetailFragment();
            movieDetailFragment.setArguments(detail);
            ((MainActivity)mApplicationContext).addFragmentMovieDetail(movieDetailFragment);
        }

        public void bindMostPlayedMovie(PosterWrapper posterLink) {
            this.poster = posterLink;
        }
    }
}
