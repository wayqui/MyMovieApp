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
import com.bustamante.jose.mymovieapp.async.MyMovieDBListAsync;
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
        MyMovieDBListAsync task;
        switch (item.getItemId()) {
            case R.id.menu_sort_popular:
                movieAdapter.actualizarPeliculas(new Movies());
                task = new MyMovieDBListAsync(this);
                task.execute(MyMovieDBListAsync.SORT_ORDER_POPULAR);
                return true;
            case R.id.menu_sort_top_rated:
                movieAdapter.actualizarPeliculas(new Movies());
                task = new MyMovieDBListAsync(this);
                task.execute(MyMovieDBListAsync.SORT_ORDER_TOP_RATED);
                return true;
            case R.id.menu_show_favorites:
                movieAdapter.actualizarPeliculas(new Movies());
                task = new MyMovieDBListAsync(this);
                task.execute(MyMovieDBListAsync.SHOW_FAVORITES);
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public MyMovieDBListAdapter getMovieAdapter() {
        return movieAdapter;
    }

    public void setMovieAdapter(MyMovieDBListAdapter movieAdapter) {
        this.movieAdapter = movieAdapter;
    }
}
