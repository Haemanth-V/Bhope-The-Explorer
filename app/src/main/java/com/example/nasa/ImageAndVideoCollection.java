package com.example.nasa;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ImageAndVideoCollection{
    private Collection collection;

    public Collection getCollection() {
        return collection;
    }

    public class Collection {

        @SerializedName("href")
        private String hrefUrl;

        @SerializedName("items")
        private List<Item> items;

        public String getHrefUrl() {
            return hrefUrl;
        }

        public List<Item> getItems() {
            return items;
        }
    }
}
