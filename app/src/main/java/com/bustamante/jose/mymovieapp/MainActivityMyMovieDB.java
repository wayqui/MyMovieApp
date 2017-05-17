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
    private GridLayoutManager glmMovieManager;
    private final int NUMERO_PELICULAS = 20;

    private final String POSICION_GRID = "posicionListado";
    private final String TIPO_GRID = "tipoListado";


    private String tipoListado;
    private Integer posicionListado;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_my_movie_db);

        MyMovieDBListAsync task = new MyMovieDBListAsync(this);

        tipoListado = MyMovieDBListAsync.SORT_ORDER_POPULAR;
        posicionListado = 0;


        if (savedInstanceState != null) {
            if (savedInstanceState.getSerializable(TIPO_GRID) != null) {
                tipoListado = (String) savedInstanceState.getSerializable(TIPO_GRID);
            }
            if (savedInstanceState.getInt(POSICION_GRID) > 0) {
                posicionListado = savedInstanceState.getInt(POSICION_GRID);
            }
        }

        task.execute(tipoListado);

        movieAdapter = new MyMovieDBListAdapter(this, NUMERO_PELICULAS, new Movies());
        rvMyMovieDB = (RecyclerView) findViewById(R.id.rv_my_movie_db_list);

        final int columns = getResources().getInteger(R.integer.gallery_columns);
        rvMyMovieDB.setLayoutManager(new GridLayoutManager(this, columns));

        rvMyMovieDB.setAdapter(movieAdapter);
        rvMyMovieDB.setHasFixedSize(true);
        if (posicionListado > 0 )
            rvMyMovieDB.setScrollY(posicionListado);
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
                tipoListado = MyMovieDBListAsync.SORT_ORDER_POPULAR;
                task.execute(tipoListado);
                return true;
            case R.id.menu_sort_top_rated:
                movieAdapter.actualizarPeliculas(new Movies());
                task = new MyMovieDBListAsync(this);
                tipoListado = MyMovieDBListAsync.SORT_ORDER_TOP_RATED;
                task.execute(tipoListado);
                return true;
            case R.id.menu_show_favorites:
                movieAdapter.actualizarPeliculas(new Movies());
                task = new MyMovieDBListAsync(this);
                tipoListado = MyMovieDBListAsync.SHOW_FAVORITES;
                task.execute(tipoListado);
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

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putSerializable(TIPO_GRID, tipoListado);
        if (rvMyMovieDB != null)
            outState.putInt(POSICION_GRID, rvMyMovieDB.getScrollY());
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        tipoListado = (String) savedInstanceState.getSerializable(TIPO_GRID);
        posicionListado = savedInstanceState.getInt(POSICION_GRID);

    }
}
