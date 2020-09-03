package com.example.nasa;

import com.google.gson.annotations.SerializedName;

public class Data {

    //@SerializedName("center")
    //private String center;

    @SerializedName("media_type")
    private String mediaType;

    @SerializedName("nasa_id")
    private String nasaID;

    @SerializedName("title")
    private String title;

    @SerializedName("description")
    private String description;

    /*public String getCenter() {
        return center;
    }*/

    public String getMediaType() {
        return mediaType;
    }

    public String getNasaID() {
        return nasaID;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }
}
