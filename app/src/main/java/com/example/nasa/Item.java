package com.example.nasa;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Item {

    @SerializedName("data")
    private List<Data> data;

    @SerializedName("href")
    private String hrefURL;

    public List<Data> getData() {
        return data;
    }

    public String getHrefURL() {
        return hrefURL;
    }
}
