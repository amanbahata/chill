package com.chillbox.app.trendingnews;


import android.content.Context;
import android.net.Uri;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.chillbox.app.R;
import com.chillbox.app.network.model.trending_news.TrendingArticle;
import com.facebook.drawee.view.SimpleDraweeView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by aman1 on 14/12/2017.
 */

public class TrendingViewAdaptor extends RecyclerView.Adapter<TrendingViewAdaptor.ViewHolder>{

    private List<TrendingArticle> mArticlesList;
    private int mRowArticle;
    private Context mApplicationContext;

    public TrendingViewAdaptor(List<TrendingArticle> mArticlesList, int mRowTrak, Context mApplicationContext) {
        this.mArticlesList = mArticlesList;
        this.mRowArticle = mRowTrak;
        this.mApplicationContext = mApplicationContext;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(this.mApplicationContext).inflate(mRowArticle, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.mArticleTitle.setText(mArticlesList.get(position).getTitle());
        holder.mPublishingDate.setText(mArticlesList.get(position).getPublishedAt());
        holder.mdrawee.setImageURI(uriParse(mArticlesList.get(position).getUrlToImage()));
        holder.bindTrendingItem(mArticlesList.get(position));
    }



    @Override
    public int getItemCount() {
        return mArticlesList.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        @BindView(R.id.movie_title) TextView mArticleTitle;
        @BindView(R.id.article_date) TextView mPublishingDate;
        @BindView(R.id.movie_artwork) SimpleDraweeView mdrawee;
        private TrendingArticle mArticle;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            itemView.setOnClickListener(this);
        }

        public void bindTrendingItem(TrendingArticle article){
            this.mArticle = article;
        }

        @Override
        public void onClick(View v) {
//            Intent i = new Intent(Intent.ACTION_VIEW, uriParse(mArticle.getUrl()));
//            mApplicationContext.startActivity(i);

            AppCompatActivity mainActivity = (AppCompatActivity) v.getContext();

            Fragment newsPageFragment = NewsPageFragment.newInstance(uriParse(mArticle.getUrl()));
            mainActivity.getSupportFragmentManager()
                    .beginTransaction()
                    .add(R.id.fragment_container, newsPageFragment)
                    .addToBackStack(null)
                    .commit();
        }
    }

    private Uri uriParse(String uri){
        Uri link = Uri.parse(uri).buildUpon().build();
        return link;
    }
}
