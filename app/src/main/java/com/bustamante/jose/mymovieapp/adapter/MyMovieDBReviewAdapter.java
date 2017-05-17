package com.bustamante.jose.mymovieapp.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.bustamante.jose.mymovieapp.R;
import com.bustamante.jose.mymovieapp.entity.Review;
import com.bustamante.jose.mymovieapp.entity.Reviews;

/**
 * Created by joselobm on 13/05/17.
 */

public class MyMovieDBReviewAdapter extends RecyclerView.Adapter<MyMovieDBReviewViewHolder>{

    private static final String LOG = com.bustamante.jose.mymovieapp.adapter.MyMovieDBReviewAdapter.class.getName();

    private Reviews reviews;

    public MyMovieDBReviewAdapter(Reviews reviews) {
        this.reviews = reviews;
    }

    @Override
    public int getItemCount() {

        if (reviews != null && reviews.getResults() != null)
            return this.reviews.getResults().size();
        return 0;
    }

    public void setReviews(Reviews reviews) {
        this.reviews = reviews;
    }

    @Override
    public MyMovieDBReviewViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).
                inflate(R.layout.my_movie_db_review_content, parent, false);

        return new MyMovieDBReviewViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MyMovieDBReviewViewHolder holder, int position) {

        final Review review = reviews.obtenerReview(position);
        holder.tvAuthor.setText(review.getAuthor());
        holder.tvContent.setText(review.getContent());
    }
}
