package com.bustamante.jose.mymovieapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.bustamante.jose.mymovieapp.entity.Movie;
import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;

public class DetailActivityMyMovieDB extends AppCompatActivity {

    private static final String LOG = com.bustamante.jose.mymovieapp.DetailActivityMyMovieDB.class.getName();

    private TextView tvTituloPelicula;
    private TextView tvSinopsisPelicula;
    private ImageView ivFondoPelicula;
    private ImageView ivPosterPelicula;
    private TextView tvFechaEstreno;
    private TextView tvPromedioVotos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_my_movie_db);

        tvTituloPelicula = (TextView) findViewById(R.id.tv_titulo_pelicula);
        tvSinopsisPelicula = (TextView) findViewById(R.id.tv_sinopsis_pelicula);
        tvFechaEstreno = (TextView) findViewById(R.id.tv_fecha_estreno);
        tvPromedioVotos = (TextView) findViewById(R.id.tv_promedio_votos);

        ivFondoPelicula = (ImageView) findViewById(R.id.iv_movie_screen_detail);
        ivPosterPelicula = (ImageView) findViewById(R.id.iv_movie_poster_detail);

        Intent intentDetallePelicula = getIntent();
        if (intentDetallePelicula.hasExtra(Movie.ACCION_SERIALIZAR_MOVIE)) {

            Movie datosPeliSeleccionada = (Movie) getIntent().getSerializableExtra(Movie.ACCION_SERIALIZAR_MOVIE);

            tvTituloPelicula.setText(datosPeliSeleccionada.getTitle());
            tvSinopsisPelicula.setText(datosPeliSeleccionada.getOverview());

            tvFechaEstreno.setText(
                    getResources().getString(R.string.cadena_fecha_estreno,
                    new SimpleDateFormat(getResources().getString(R.string.formato_fecha)).format(datosPeliSeleccionada.getReleaseDate())));

            tvPromedioVotos.setText(
                    getResources().getString(R.string.cadena_puntuacion,
                    datosPeliSeleccionada.getVoteAverage()));

            Picasso.with(this).load(
                    datosPeliSeleccionada.getImageUrl(
                            Movie.TIPO_IMAGEN_FONDO, Movie.TAMANO_IMAGEN_GRANDE)).into(ivFondoPelicula);

            Picasso.with(this).load(
                    datosPeliSeleccionada.getImageUrl(
                            Movie.TIPO_IMAGEN_POSTER, Movie.TAMANO_IMAGEN_PEQUENO)).into(ivPosterPelicula);

        }

    }
}
