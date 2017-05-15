package com.bustamante.jose.mymovieapp.provider;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import static com.bustamante.jose.mymovieapp.provider.MovieDBContract.MovieDBEntry;

/**
 * Created by joselobm on 13/05/17.
 */

public class MovieDBHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "moviesDb.db";

    private static final int VERSION = 1;

    MovieDBHelper(Context context) {
        super(context, DATABASE_NAME, null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        final String CREATE_TABLE = "CREATE TABLE "  + MovieDBEntry.TABLE_NAME + " (" +
                MovieDBEntry._ID                + " INTEGER PRIMARY KEY, " +
                MovieDBEntry.COLUMN_MOVIE_ID    + " TEXT NOT NULL, " +
                MovieDBEntry.COLUMN_TITLE       + " TEXT NOT NULL, " +
                MovieDBEntry.COLUMN_RELEASE     + " TEXT NOT NULL, " +
                MovieDBEntry.COLUMN_SCORE       + " TEXT NOT NULL, " +
                MovieDBEntry.COLUMN_SYNOPSIS    + " TEXT NOT NULL, " +
                MovieDBEntry.COLUMN_POSTER_BIG  + " TEXT NOT NULL, " +
                MovieDBEntry.COLUMN_POSTER_SMALL+ " TEXT NOT NULL);";

        db.execSQL(CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + MovieDBEntry.TABLE_NAME);
        onCreate(db);
    }
}
