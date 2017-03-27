package com.bustamante.jose.mymovieapp.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bustamante.jose.mymovieapp.DetailActivityMyMovieDB;
import com.bustamante.jose.mymovieapp.R;
import com.bustamante.jose.mymovieapp.entity.Movie;
import com.bustamante.jose.mymovieapp.entity.Movies;
import com.squareup.picasso.Picasso;

/**
 * Created by joselobm on 19/02/17.
 */

public class MyMovieDBListAdapter extends RecyclerView.Adapter<MyMovieDBListAdapter.MyMovieDBListViewHolder>{

    private static final String LOG = com.bustamante.jose.mymovieapp.adapter.MyMovieDBListAdapter.class.getName();

    private Context contexto;
    private int numeroPeliculas;
    private Movies peliculas;

    public MyMovieDBListAdapter(Context contexto, int numeroPeliculas, Movies peliculas) {
        this.contexto = contexto;
        this.numeroPeliculas = numeroPeliculas;
        this.peliculas = peliculas;
    }

    @Override
    public int getItemCount() {
        return this.numeroPeliculas;
    }

    @Override
    public MyMovieDBListViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).
                inflate(R.layout.my_movie_db_list_content, parent, false);

        return new MyMovieDBListViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MyMovieDBListViewHolder holder, int position) {
        final Movie pelicula = obtenerPelicula(position);

        if (pelicula == null) return;

        Picasso.with(contexto).load(pelicula.getImageUrl(Movie.TIPO_IMAGEN_POSTER, Movie.TAMANO_IMAGEN_PEQUENO)).into(holder.mImageView);

        holder.mImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentDetalleMovie = new Intent(contexto, DetailActivityMyMovieDB.class);
                intentDetalleMovie.putExtra(Movie.ACCION_SERIALIZAR_MOVIE, pelicula);
                contexto.startActivity(intentDetalleMovie);

            }
        });
    }

    private Movie obtenerPelicula(int position) {
        if (this.peliculas != null && this.peliculas.getResults() != null && this.peliculas.getResults().size() != 0)  {
            return this.peliculas.getResults().get(position);
        }
        return null;
    }

    public void actualizarPeliculas(Movies pelis) {
        this.peliculas = pelis;
    }

    /**
     * MyMovieDBListViewHolder
     */
    class MyMovieDBListViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        public final ImageView mImageView;

        public MyMovieDBListViewHolder(View itemView) {
            super(itemView);
            mView = itemView;
            mImageView = (ImageView) itemView.findViewById(R.id.iv_movie_screenshot);
        }
    }

}
