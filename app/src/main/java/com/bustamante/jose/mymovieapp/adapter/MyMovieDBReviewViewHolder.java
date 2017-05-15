package com.bustamante.jose.mymovieapp.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bustamante.jose.mymovieapp.R;

/**
 * Created by joselobm on 13/05/17.
 */

public class MyMovieDBReviewViewHolder extends RecyclerView.ViewHolder {

    private static final String LOG = com.bustamante.jose.mymovieapp.adapter.MyMovieDBReviewViewHolder.class.getName();

    public final View mView;
    public final TextView tvAuthor;
    public final TextView tvContent;


    public MyMovieDBReviewViewHolder(View itemView) {
        super(itemView);

        mView = itemView;
        tvAuthor = (TextView) itemView.findViewById(R.id.tv_review_author);
        tvContent = (TextView) itemView.findViewById(R.id.tv_review_content);
    }
}
