package com.bustamante.jose.mymovieapp.entity;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by joselobm on 13/05/17.
 */

public class Trailer implements Serializable {
    @SerializedName("name")
    @Expose
    private String name;

    @SerializedName("size")
    @Expose
    private String size;

    @SerializedName("source")
    @Expose
    private String source;

    @SerializedName("type")
    @Expose
    private String type;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Trailer(String name, String size, String source, String type) {
        this.name = name;
        this.size = size;
        this.source = source;
        this.type = type;
    }

    public Trailer() {
        this.name = null;
        this.size = null;
        this.source = null;
        this.type = null;
    }
}
