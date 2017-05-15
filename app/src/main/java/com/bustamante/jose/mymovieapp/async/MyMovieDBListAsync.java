package com.bustamante.jose.mymovieapp.async;

import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;
import android.util.Log;

import com.bustamante.jose.mymovieapp.BuildConfig;
import com.bustamante.jose.mymovieapp.MainActivityMyMovieDB;
import com.bustamante.jose.mymovieapp.R;
import com.bustamante.jose.mymovieapp.entity.Movie;
import com.bustamante.jose.mymovieapp.entity.Movies;
import com.bustamante.jose.mymovieapp.provider.MovieDBContract;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by joselobm on 13/05/17.
 */

public class MyMovieDBListAsync extends AsyncTask<String, Void, Movies> {

    private static final String LOG = com.bustamante.jose.mymovieapp.async.MyMovieDBListAsync.class.getName();

    public static final String SORT_ORDER_POPULAR = "POPULAR";
    public static final String SORT_ORDER_TOP_RATED = "TOP_RATED";
    public static final String SHOW_FAVORITES = "FAVORITES";

    private final String[] movieProjection = new String[]{
            MovieDBContract.MovieDBEntry.COLUMN_MOVIE_ID,
            MovieDBContract.MovieDBEntry.COLUMN_TITLE,
            MovieDBContract.MovieDBEntry.COLUMN_RELEASE,
            MovieDBContract.MovieDBEntry.COLUMN_SCORE,
            MovieDBContract.MovieDBEntry.COLUMN_SYNOPSIS,
            MovieDBContract.MovieDBEntry.COLUMN_POSTER_BIG,
            MovieDBContract.MovieDBEntry.COLUMN_POSTER_SMALL
    };

    private final Context contextoMyMovieDBList;

    public MyMovieDBListAsync(Context context) {
        contextoMyMovieDBList = context;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected Movies doInBackground(String... params) {

        if (SHOW_FAVORITES.equals(params[0])) {
            // Recuperamos las ordenes
            Cursor cursorFavoritos = contextoMyMovieDBList.getContentResolver().
                    query(MovieDBContract.MovieDBEntry.CONTENT_URI, movieProjection, null, null, null);

            Movies peliculasFavoritas = new Movies();

            List<Movie> listaPelis = new ArrayList<Movie>();

            if (cursorFavoritos.moveToFirst()) {
                do {
                    Long idq = cursorFavoritos.getLong(cursorFavoritos.getColumnIndex(MovieDBContract.MovieDBEntry.COLUMN_MOVIE_ID));
                    Log.i(LOG, "id peli = "+idq.toString());
                    String[] parametros = new String[1];
                    parametros[0] = idq.toString();
                    Uri uri = construirUri(parametros);
                    StringBuilder strBuildMovies = obtenerPeliculasDeIMDB(uri);
                    listaPelis.add(obtenerListaObjetosMovie(strBuildMovies));
                } while (cursorFavoritos.moveToNext());
            }

            peliculasFavoritas.setResults(listaPelis);
            Log.i(LOG, "numero pelis = "+Integer.toString(listaPelis.size()));

            return peliculasFavoritas;


        } else {
            Uri uri = construirUri(params);
            StringBuilder strBuildMovies = obtenerPeliculasDeIMDB(uri);
            if (null != strBuildMovies)
                return obtenerListaObjetoMovies(strBuildMovies);
        }


        return null;
    }

    @Override
    protected void onPostExecute(Movies s) {
        ((MainActivityMyMovieDB) contextoMyMovieDBList).getMovieAdapter().actualizarPeliculas(s);
        ((MainActivityMyMovieDB) contextoMyMovieDBList).getMovieAdapter().notifyDataSetChanged();

    }

    private StringBuilder obtenerPeliculasDeIMDB(Uri uri) {
        URL url = null;
        StringBuilder stringBuilder = null;
        try {
            url = new URL(uri.toString());
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            BufferedReader reader = new BufferedReader(
                    new InputStreamReader(connection.getInputStream()));
            stringBuilder = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                stringBuilder.append(line).append("\n");
            }
            reader.close();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return stringBuilder;
    }

    private Movies obtenerListaObjetoMovies(StringBuilder jsonPelis) {
        Gson gSon=  new GsonBuilder().setDateFormat(contextoMyMovieDBList.getString(R.string.formato_fecha)).create();
        return gSon.fromJson(jsonPelis.toString(), Movies.class);
    }

    private Movie obtenerListaObjetosMovie(StringBuilder jsonPelis) {
        Gson gSon=  new GsonBuilder().setDateFormat(contextoMyMovieDBList.getString(R.string.formato_fecha)).create();
        return gSon.fromJson(jsonPelis.toString(), Movie.class);
    }

    private Uri construirUri(String[] params) {

        Uri uri = new Uri.Builder()
                .scheme(contextoMyMovieDBList.getString(R.string.url_prefijo))
                .authority(contextoMyMovieDBList.getString(R.string.url_imdb))
                .appendPath(contextoMyMovieDBList.getString(R.string.url_imdb_v3))
                .appendPath(contextoMyMovieDBList.getString(R.string.url_imdb_pelicula))
                .appendPath(params[0].toLowerCase())
                .appendQueryParameter(contextoMyMovieDBList.getString(R.string.url_imdb_api), BuildConfig.MOVIEDB_API_KEY)
                .build();
        Log.i(LOG, uri.toString());
        return uri;
    }
}