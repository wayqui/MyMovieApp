package com.bustamante.jose.mymovieapp.async;

import android.content.Context;
import android.net.Uri;
import android.os.AsyncTask;
import android.util.Log;

import com.bustamante.jose.mymovieapp.BuildConfig;
import com.bustamante.jose.mymovieapp.DetailActivityMyMovieDB;
import com.bustamante.jose.mymovieapp.R;
import com.bustamante.jose.mymovieapp.entity.Trailers;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by joselobm on 13/05/17.
 */

public class MyMovieDBDetailTrailersAsync extends AsyncTask<String, Void, Trailers> {
    private static final String LOG = MyMovieDBDetailTrailersAsync.class.getName();

    private final Context contextoMyMovieDBDetail;

    public MyMovieDBDetailTrailersAsync(Context context) {
        contextoMyMovieDBDetail = context;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected Trailers doInBackground(String... params) {
        Uri uri = construirUri(params);
        StringBuilder sbTrailers = obtenerPeliculasDeIMDB(uri);
        if (null != sbTrailers) {
            return obtenerListaTrailersMovie(sbTrailers);
        }else{
            return null;
        }
    }

    @Override
    protected void onPostExecute(Trailers s) {
        ((DetailActivityMyMovieDB) contextoMyMovieDBDetail).getTrailerAdapter().setTrailers(s);
        ((DetailActivityMyMovieDB) contextoMyMovieDBDetail).getTrailerAdapter().notifyDataSetChanged();
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

    private Trailers obtenerListaTrailersMovie(StringBuilder jsonPelis) {
        Log.i(LOG, jsonPelis.toString());
        Gson gSon=  new GsonBuilder().setDateFormat(
                contextoMyMovieDBDetail.getString(R.string.formato_fecha)).create();
        return gSon.fromJson(jsonPelis.toString(), Trailers.class);
    }

    private Uri construirUri(String[] params) {
        Uri uri = new Uri.Builder()
                .scheme(contextoMyMovieDBDetail.getString(R.string.url_prefijo))
                .authority(contextoMyMovieDBDetail.getString(R.string.url_imdb))
                .appendPath(contextoMyMovieDBDetail.getString(R.string.url_imdb_v3))
                .appendPath(contextoMyMovieDBDetail.getString(R.string.url_imdb_pelicula))
                .appendPath(params[0].toLowerCase())
                .appendPath(contextoMyMovieDBDetail.getString(R.string.url_imdb_trailers))
                .appendQueryParameter(contextoMyMovieDBDetail.getString(R.string.url_imdb_api), BuildConfig.MOVIEDB_API_KEY)
                .build();
        Log.i(LOG, uri.toString());
        return uri;
    }
}
