package com.bustamante.jose.mymovieapp.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.bustamante.jose.mymovieapp.R;

/**
 * Created by joselobm on 13/05/17.
 */

public class MyMovieDBTrailerViewHolder extends RecyclerView.ViewHolder {

    private static final String LOG = com.bustamante.jose.mymovieapp.adapter.MyMovieDBTrailerViewHolder.class.getName();

    public final View mView;
    public final TextView tvEnlace;


    public MyMovieDBTrailerViewHolder(View itemView) {
        super(itemView);
        mView = itemView;
        tvEnlace = (TextView) itemView.findViewById(R.id.tv_trailer_url);
    }
}
