package com.bustamante.jose.mymovieapp;

import android.content.Context;
import android.net.Uri;
import android.os.AsyncTask;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.bustamante.jose.mymovieapp.adapter.MyMovieDBListAdapter;
import com.bustamante.jose.mymovieapp.entity.Movies;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class MainActivityMyMovieDB extends AppCompatActivity {

    private static final String LOG = com.bustamante.jose.mymovieapp.MainActivityMyMovieDB.class.getName();

    private RecyclerView rvMyMovieDB;
    private MyMovieDBListAdapter movieAdapter;
    private final int NUMERO_PELICULAS = 20;
    private final int NUMERO_COLUMNAS = 3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_my_movie_db);

        MyMovieDBListAsync task = new MyMovieDBListAsync(this);
        task.execute(MyMovieDBListAsync.SORT_ORDER_POPULAR);

        movieAdapter = new MyMovieDBListAdapter(this, NUMERO_PELICULAS, new Movies());

        rvMyMovieDB = (RecyclerView) findViewById(R.id.rv_my_movie_db_list);
        rvMyMovieDB.setLayoutManager(new GridLayoutManager(this, NUMERO_COLUMNAS));
        rvMyMovieDB.setAdapter(movieAdapter);
        rvMyMovieDB.setHasFixedSize(true);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.my_movie_db_list_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_sort_popular:
                movieAdapter.actualizarPeliculas(new Movies());
                MyMovieDBListAsync task = new MyMovieDBListAsync(this);
                task.execute(MyMovieDBListAsync.SORT_ORDER_POPULAR);
                return true;
            case R.id.menu_sort_top_rated:
                movieAdapter.actualizarPeliculas(new Movies());
                MyMovieDBListAsync task2 = new MyMovieDBListAsync(this);
                task2.execute(MyMovieDBListAsync.SORT_ORDER_TOP_RATED);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public class MyMovieDBListAsync extends AsyncTask<String, Void, Movies> {

        public static final String SORT_ORDER_POPULAR = "POPULAR";
        public static final String SORT_ORDER_TOP_RATED = "TOP_RATED";

        private final Context contextoMyMovieDBList;

        public MyMovieDBListAsync(Context context) {
            contextoMyMovieDBList = context;
        }

        @Override
        protected Movies doInBackground(String... params) {
            Uri uri = construirUri(params);
            StringBuilder movies = obtenerPeliculasDeIMDB(uri);
            if (null != movies)
                return obtenerListaObjetosMovie(movies);
            return null;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected void onPostExecute(Movies s) {
            movieAdapter.actualizarPeliculas(s);
            movieAdapter.notifyDataSetChanged();
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

        private Movies obtenerListaObjetosMovie(StringBuilder jsonPelis) {
            Gson gSon=  new GsonBuilder().setDateFormat(contextoMyMovieDBList.getString(R.string.formato_fecha)).create();
            return gSon.fromJson(jsonPelis.toString(), Movies.class);
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
}
