package com.bustamante.jose.mymovieapp.adapter;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.text.method.LinkMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bustamante.jose.mymovieapp.DetailActivityMyMovieDB;
import com.bustamante.jose.mymovieapp.R;
import com.bustamante.jose.mymovieapp.entity.Trailer;
import com.bustamante.jose.mymovieapp.entity.Trailers;

/**
 * Created by joselobm on 13/05/17.
 */

public class MyMovieDBTrailerAdapter extends RecyclerView.Adapter<MyMovieDBTrailerViewHolder>{

    private static final String LOG = com.bustamante.jose.mymovieapp.adapter.MyMovieDBTrailerAdapter.class.getName();

    private Trailers trailers;

    public Context getContexto() {
        return contexto;
    }

    public void setContexto(Context contexto) {
        this.contexto = contexto;
    }

    private Context contexto;


    public MyMovieDBTrailerAdapter(Context contexto, Trailers trailers) {
        this.trailers = trailers;
        this.contexto = contexto;
    }

    @Override
    public int getItemCount() {
        return this.trailers.getResults().size();
    }

    public void setTrailers(Trailers trailers) {
        this.trailers = trailers;
    }

    @Override
    public MyMovieDBTrailerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).
                inflate(R.layout.my_movie_db_trailers_content, parent, false);

        return new MyMovieDBTrailerViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MyMovieDBTrailerViewHolder holder, int position) {

        final Trailer trailer = trailers.obtenerTrailer(position);
        //TODO Componer correctamente la URL para youtube

        holder.tvEnlace.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent applicationIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("vnd.youtube:" + trailer.getSource()));
                Intent browserIntent = new Intent(Intent.ACTION_VIEW,
                        Uri.parse("http://www.youtube.com/watch?v=" + trailer.getSource()));
                try {
                    ((DetailActivityMyMovieDB)getContexto()).startActivity(applicationIntent);
                } catch (ActivityNotFoundException ex) {
                    ((DetailActivityMyMovieDB)getContexto()).startActivity(browserIntent);
                }
            }
        });

        holder.tvEnlace.setText(trailer.getName());
    }
}
