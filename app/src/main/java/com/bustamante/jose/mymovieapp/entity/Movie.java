package com.bustamante.jose.mymovieapp.entity;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Model for a movie and its properties.
 */
public class Movie implements Serializable {

    private static final String IMG_URL_BASE = "https://image.tmdb.org/t/p/";
    private static final String IMG_URL_SMALL = "w342/";
    private static final String IMG_URL_BIG = "w780/";

    public static final String ACCION_SERIALIZAR_MOVIE = "action_serializar_movie";
    public static final int TIPO_IMAGEN_POSTER = 1;
    public static final int TIPO_IMAGEN_FONDO = 2;

    public static final int TAMANO_IMAGEN_PEQUENO = 1;
    public static final int TAMANO_IMAGEN_GRANDE = 2;

    @SerializedName("id")
    @Expose
    private int id;

    @SerializedName("original_title")
    @Expose
    private String originalTitle;

    @SerializedName("original_language")
    @Expose
    private String originalLanguage;

    @SerializedName("title")
    @Expose
    private String title;

    @SerializedName("backdrop_path")
    @Expose
    private String backdropPath;

    @SerializedName("popularity")
    @Expose
    private float popularity;

    @SerializedName("vote_count")
    @Expose
    private int voteCount;

    @SerializedName("video")
    @Expose
    private boolean video;

    @SerializedName("vote_average")
    @Expose
    private float voteAverage;

    @SerializedName("poster_path")
    @Expose
    private String posterPath;

    @SerializedName("adult")
    @Expose
    private boolean adult;

    @SerializedName("overview")
    @Expose
    private String overview;

    @SerializedName("release_date")
    @Expose
    private Date releaseDate;

    @SerializedName("genre_ids")
    @Expose
    private List<Integer> genreIds = null;

    @SerializedName("results")
    @Expose
    private List<String> reviews = null;

    @SerializedName("youtube")
    @Expose
    private List<String> trailers = null;

    public Movie(int id, String originalTitle, String originalLanguage, String title, String backdropPath,
                 float popularity, int voteCount, boolean video, float voteAverage, String posterPath,
                 boolean adult, String overview, Date releaseDate, List<Integer> genreIds,
                 List<String> reviews, List<String> trailers) {
        super();
        this.id = id;
        this.originalTitle = originalTitle;
        this.originalLanguage = originalLanguage;
        this.title = title;
        this.backdropPath = backdropPath;
        this.popularity = popularity;
        this.voteCount = voteCount;
        this.video = video;
        this.voteAverage = voteAverage;
        this.posterPath = posterPath;
        this.adult = adult;
        this.overview = overview;
        this.releaseDate = releaseDate;
        this.genreIds = genreIds;
        this.reviews = reviews;
        this.trailers = trailers;
    }

    public Movie() {
        super();
        this.id = 0;
        this.originalTitle = null;
        this.originalLanguage = null;
        this.title = null;
        this.backdropPath = null;
        this.popularity = 0;
        this.voteCount = 0;
        this.video = false;
        this.voteAverage = 0;
        this.posterPath = null;
        this.adult = false;
        this.overview = null;
        this.releaseDate = null;
        this.genreIds = null;
        this.reviews = null;
        this.trailers = null;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getOriginalTitle() {
        return originalTitle;
    }

    public void setOriginalTitle(String originalTitle) {
        this.originalTitle = originalTitle;
    }

    public String getOriginalLanguage() {
        return originalLanguage;
    }

    public void setOriginalLanguage(String originalLanguage) {
        this.originalLanguage = originalLanguage;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getBackdropPath() {
        return backdropPath;
    }

    public void setBackdropPath(String backdropPath) {
        this.backdropPath = backdropPath;
    }

    public float getPopularity() {
        return popularity;
    }

    public void setPopularity(float popularity) {
        this.popularity = popularity;
    }

    public int getVoteCount() {
        return voteCount;
    }

    public void setVoteCount(int voteCount) {
        this.voteCount = voteCount;
    }

    public boolean isVideo() {
        return video;
    }

    public void setVideo(boolean video) {
        this.video = video;
    }

    public float getVoteAverage() {
        return voteAverage;
    }

    public void setVoteAverage(float voteAverage) {
        this.voteAverage = voteAverage;
    }

    public String getPosterPath() {
        return posterPath;
    }

    public void setPosterPath(String posterPath) {
        this.posterPath = posterPath;
    }

    public boolean isAdult() {
        return adult;
    }

    public void setAdult(boolean adult) {
        this.adult = adult;
    }

    public String getOverview() {
        return overview;
    }

    public void setOverview(String overview) {
        this.overview = overview;
    }

    public Date getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(Date releaseDate) {
        this.releaseDate = releaseDate;
    }

    public List<Integer> getGenreIds() {
        return genreIds;
    }

    public void setGenreIds(List<Integer> genreIds) {
        this.genreIds = genreIds;
    }

    public List<String> getReviews() {return reviews;}

    public List<String> getTrailers() {return trailers;}

    public void setReviews(List<String> reviews) {this.reviews = reviews;}

    public void setTrailers(List<String> trailers) {this.trailers = trailers;}

    public String getImageUrl(int tipoImagen, int tamanoImagen) {
        String imagenSeleccionada = (tipoImagen == Movie.TIPO_IMAGEN_POSTER ? this.getPosterPath():this.getBackdropPath());
        String tamanoSeleccionado = (tamanoImagen == Movie.TAMANO_IMAGEN_PEQUENO ? Movie.IMG_URL_SMALL:Movie.IMG_URL_BIG);
        return IMG_URL_BASE + tamanoSeleccionado + imagenSeleccionada;
    }
}
