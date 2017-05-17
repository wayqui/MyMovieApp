package com.bustamante.jose.mymovieapp;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.view.ViewCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bustamante.jose.mymovieapp.adapter.MyMovieDBReviewAdapter;
import com.bustamante.jose.mymovieapp.adapter.MyMovieDBTrailerAdapter;
import com.bustamante.jose.mymovieapp.async.MyMovieDBDetailReviewsAsync;
import com.bustamante.jose.mymovieapp.async.MyMovieDBDetailTrailersAsync;
import com.bustamante.jose.mymovieapp.async.MyMovieDBListAsync;
import com.bustamante.jose.mymovieapp.entity.Movie;

import com.bustamante.jose.mymovieapp.entity.Reviews;
import com.bustamante.jose.mymovieapp.entity.Trailers;
import com.bustamante.jose.mymovieapp.provider.MovieDBContract;
import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DetailActivityMyMovieDB extends AppCompatActivity {

    private static final String LOG = com.bustamante.jose.mymovieapp.DetailActivityMyMovieDB.class.getName();

    private final int NUMERO_MAX_REVIEWS = 20;

    private TextView tvTituloPelicula;
    private TextView tvSinopsisPelicula;
    private ImageView ivFondoPelicula;
    private ImageView ivPosterPelicula;
    private TextView tvFechaEstreno;
    private TextView tvPromedioVotos;
    private MyMovieDBReviewAdapter reviewAdapter;
    private RecyclerView rvReviews;
    private MyMovieDBTrailerAdapter trailerAdapter;
    private RecyclerView rvTrailers;
    private CollapsingToolbarLayout collapsingToolbarLayout;

    private Movie datosPeliSeleccionada;

    private Boolean boolAddToFavorites;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_my_movie_db);

        ViewCompat.setTransitionName(findViewById(R.id.app_bar_layout), "NOMBRE_TRANS");


        String itemTitle = "TITULO";
        collapsingToolbarLayout = (CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar);
        collapsingToolbarLayout.setTitle(itemTitle);
        collapsingToolbarLayout.setExpandedTitleColor(getResources().getColor(android.R.color.transparent));


        tvTituloPelicula = (TextView) findViewById(R.id.tv_titulo_pelicula);
        tvSinopsisPelicula = (TextView) findViewById(R.id.tv_sinopsis_pelicula);
        tvFechaEstreno = (TextView) findViewById(R.id.tv_fecha_estreno);
        tvPromedioVotos = (TextView) findViewById(R.id.tv_promedio_votos);

        ivFondoPelicula = (ImageView) findViewById(R.id.iv_movie_screen_detail);
        ivPosterPelicula = (ImageView) findViewById(R.id.iv_movie_poster_detail);

        Intent intentDetallePelicula = getIntent();
        if (intentDetallePelicula.hasExtra(Movie.ACCION_SERIALIZAR_MOVIE)) {

            datosPeliSeleccionada = (Movie) getIntent().getSerializableExtra(Movie.ACCION_SERIALIZAR_MOVIE);

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

            MyMovieDBDetailReviewsAsync asyncReviews = new MyMovieDBDetailReviewsAsync(this);
            asyncReviews.execute(Integer.toString(datosPeliSeleccionada.getId()));

            MyMovieDBDetailTrailersAsync asyncTrailers = new MyMovieDBDetailTrailersAsync(this);
            asyncTrailers.execute(Integer.toString(datosPeliSeleccionada.getId()));

            reviewAdapter = new MyMovieDBReviewAdapter(new Reviews());
            rvReviews = (RecyclerView) findViewById(R.id.rv_my_movie_db_reviews);
            rvReviews.setLayoutManager(new LinearLayoutManager(this));
            rvReviews.setAdapter(reviewAdapter);
            rvReviews.setHasFixedSize(true);

            trailerAdapter = new MyMovieDBTrailerAdapter(this, new Trailers());
            rvTrailers = (RecyclerView) findViewById(R.id.rv_my_movie_db_trailers);
            rvTrailers.setLayoutManager(new LinearLayoutManager(this));
            rvTrailers.setAdapter(trailerAdapter);
            rvTrailers.setHasFixedSize(true);
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.my_movie_db_detail_menu, menu);
        boolAddToFavorites = true;
        if (buscarPeliculaFavoritaXID(Integer.toString(datosPeliSeleccionada.getId()))) {
            MenuItem opcionFavoritos = menu.findItem(R.id.menu_add_favorites);
            opcionFavoritos.setTitle(R.string.menu_item_remove_favorites);
            boolAddToFavorites = false;
        }

        return true;
    }

    private Boolean buscarPeliculaFavoritaXID(String idPelicula) {
        String[] projection = new String[] {MovieDBContract.MovieDBEntry.COLUMN_MOVIE_ID};
        String selection = MovieDBContract.MovieDBEntry.COLUMN_MOVIE_ID + "=?";
        String[] selectionArgs = new String[] {idPelicula};

        Cursor resultado = getContentResolver().query(MovieDBContract.MovieDBEntry.CONTENT_URI, projection, selection, selectionArgs, null);

        return (resultado.getCount() > 0);
    }

    private int eliminarPeliculaDeFavoritos(String idPelicula) {
        String selection = MovieDBContract.MovieDBEntry.COLUMN_MOVIE_ID + "=?";
        String[] selectionArgs = new String[] {idPelicula};
        return getContentResolver().delete(MovieDBContract.MovieDBEntry.CONTENT_URI, selection, selectionArgs);
    }

    private Uri aniadirPeliculaAFavoritos() {
        ContentValues valores = new ContentValues();

        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-mm-dd");
        String fechaRelease = formatter.format(datosPeliSeleccionada.getReleaseDate());

        valores.put(MovieDBContract.MovieDBEntry.COLUMN_MOVIE_ID, datosPeliSeleccionada.getId());
        valores.put(MovieDBContract.MovieDBEntry.COLUMN_TITLE, datosPeliSeleccionada.getTitle());
        valores.put(MovieDBContract.MovieDBEntry.COLUMN_RELEASE, fechaRelease);
        valores.put(MovieDBContract.MovieDBEntry.COLUMN_SCORE, datosPeliSeleccionada.getPopularity());
        valores.put(MovieDBContract.MovieDBEntry.COLUMN_SYNOPSIS, datosPeliSeleccionada.getOverview());
        valores.put(MovieDBContract.MovieDBEntry.COLUMN_POSTER_BIG, datosPeliSeleccionada.getBackdropPath());
        valores.put(MovieDBContract.MovieDBEntry.COLUMN_POSTER_SMALL, datosPeliSeleccionada.getPosterPath());

        return getContentResolver().insert(MovieDBContract.MovieDBEntry.CONTENT_URI, valores);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_add_favorites:

                if (boolAddToFavorites) {
                    Uri resultado = this.aniadirPeliculaAFavoritos();
                    if (resultado != null) {
                        Toast.makeText(getBaseContext(), resultado.toString(), Toast.LENGTH_LONG).show();
                    }
                } else {
                    int numeroBorrados = this.eliminarPeliculaDeFavoritos(Integer.toString(datosPeliSeleccionada.getId()));
                    if (numeroBorrados > 0) {
                        Toast.makeText(getBaseContext(), "Se han borrado "+numeroBorrados+" elementos", Toast.LENGTH_LONG).show();
                    }
                }
                boolAddToFavorites = !(boolAddToFavorites);
                finish();
                updateMenuTitles(item);

                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }


    public MyMovieDBReviewAdapter getReviewAdapter() {
        return reviewAdapter;
    }

    public void setReviewAdapter(MyMovieDBReviewAdapter reviewAdapter) {
        this.reviewAdapter = reviewAdapter;
    }

    public MyMovieDBTrailerAdapter getTrailerAdapter() {
        return trailerAdapter;
    }

    public void setTrailerAdapter(MyMovieDBTrailerAdapter trailerAdapter) {
        this.trailerAdapter = trailerAdapter;
    }

    private void updateMenuTitles(MenuItem bedMenuItem) {
        if (boolAddToFavorites) {
            bedMenuItem.setTitle(R.string.menu_item_add_favorites);
        } else {
            bedMenuItem.setTitle(R.string.menu_item_remove_favorites);
        }
    }
}
