package com.bustamante.jose.mymovieapp.provider;

import android.net.Uri;
import android.provider.BaseColumns;

/**
 * Created by joselobm on 13/05/17.
 */

public class MovieDBContract {

    public static final String AUTHORITY = "com.bustamante.jose.mymovieapp";

    public static final Uri BASE_CONTENT_URI = Uri.parse("content://" + AUTHORITY);

    public static final String PATH_MOVIE = "movie";

    public static final class MovieDBEntry implements BaseColumns {

        public static final Uri CONTENT_URI = BASE_CONTENT_URI.buildUpon().appendPath(PATH_MOVIE).build();

        // Task table and column names
        public static final String TABLE_NAME = "movie";

        public static final String COLUMN_MOVIE_ID = "movie_id";
        public static final String COLUMN_TITLE = "titulo";
        public static final String COLUMN_RELEASE = "estreno";
        public static final String COLUMN_SCORE = "puntuacion";
        public static final String COLUMN_SYNOPSIS = "sinopsis";
        public static final String COLUMN_POSTER_BIG = "poster_grande";
        public static final String COLUMN_POSTER_SMALL = "poster_pequeno";

    }

}
