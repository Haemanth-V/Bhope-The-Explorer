package com.example.nasa;

import com.google.gson.annotations.SerializedName;

public class APOD {

    @SerializedName("date")
    private String date;

    @SerializedName("explanation")
    private String explanation;

    @SerializedName("hdurl")
    private String hdUrl;

    @SerializedName("media_type")
    private String mediaType;

    @SerializedName("service_version")
    private String serviceVersion;

    @SerializedName("title")
    private String title;

    @SerializedName("url")
    private String url;

    public String getDate() {
        return date;
    }

    public String getExplanation() {
        return explanation;
    }

    public String getHdUrl() {
        return hdUrl;
    }

    public String getMediaType() {
        return mediaType;
    }

    public String getServiceVersion() {
        return serviceVersion;
    }

    public String getTitle() {
        return title;
    }

    public String getUrl() {
        return url;
    }
}
