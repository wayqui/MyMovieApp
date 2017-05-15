package com.bustamante.jose.mymovieapp.entity;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by joselobm on 13/05/17.
 */

public class Trailers {

    @SerializedName("id")
    @Expose
    private Integer id;

    @SerializedName("youtube")
    @Expose
    private List<Trailer> results = null;

    public Trailers(Integer id, List<Trailer> results) {
        this.id = id;
        this.results = results;
    }

    public Trailers() {
        this.id = 0;
        this.results = new ArrayList<>();
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public List<Trailer> getResults() {
        return results;
    }

    public void setResults(List<Trailer> results) {
        this.results = results;
    }

    public Trailer obtenerTrailer(int position) {
        if (this.getResults() != null && this.getResults().size() != 0)  {
            return this.getResults().get(position);
        }
        return null;
    }
}
