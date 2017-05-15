package com.bustamante.jose.mymovieapp.provider;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.support.annotation.Nullable;

/**
 * Created by joselobm on 13/05/17.
 */

public class MovieDBContentProvider extends ContentProvider{

    public static final int MOVIES = 100;
    public static final int MOVIE_WITH_ID = 101;

    private static final UriMatcher sUriMatcher = buildUriMatcher();

    private MovieDBHelper myTaskDBHelper;

    @Override
    public boolean onCreate() {
        Context context = getContext();
        myTaskDBHelper = new MovieDBHelper(context);
        return true;
    }

    @Nullable
    @Override
    public Uri insert(Uri uri, ContentValues values) {
        final SQLiteDatabase baseDatosSQLite = myTaskDBHelper.getWritableDatabase();

        Uri uriInsert;

        switch (sUriMatcher.match(uri)) {
            case MOVIES:
                long result = baseDatosSQLite.insert(MovieDBContract.MovieDBEntry.TABLE_NAME, null, values);
                if (result > 0) {
                    uriInsert = ContentUris.withAppendedId(MovieDBContract.MovieDBEntry.CONTENT_URI, result);
                } else {
                    throw new SQLException("Fallo al ejecutar el insert "+uri);
                }
                break;

            default:
                throw new UnsupportedOperationException("No se pudo mapear la URI "+uri);
        }
        getContext().getContentResolver().notifyChange(uri, null);

        return uriInsert;
    }

    @Nullable
    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
        final SQLiteDatabase baseDatosSQLite = myTaskDBHelper.getReadableDatabase();

        Cursor retCursor;

        switch (sUriMatcher.match(uri)) {
            case MOVIES:
                retCursor = baseDatosSQLite.query(MovieDBContract.MovieDBEntry.TABLE_NAME,
                        projection,
                        selection,
                        selectionArgs,
                        null,
                        null,
                        sortOrder
                );
                break;

            default:
                throw new UnsupportedOperationException("No se pudo mapear la URI "+uri);
        }
        getContext().getContentResolver().notifyChange(uri, null);

        return retCursor;
    }

    @Nullable
    @Override
    public String getType(Uri uri) {
        return null;
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        return 0;
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
        return 0;
    }

    public static UriMatcher buildUriMatcher() {
        UriMatcher uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
        uriMatcher.addURI(MovieDBContract.AUTHORITY, MovieDBContract.PATH_MOVIE, MOVIES);
        uriMatcher.addURI(MovieDBContract.AUTHORITY, MovieDBContract.PATH_MOVIE + "/#", MOVIE_WITH_ID);
        return uriMatcher;
    }
}
