package com.example.nasa;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class NasaAssetIDCollection {
     public class Collection{
         public class Item{
             @SerializedName("href")
             private String url;

             public String getUrl() {
                 return url;
             }
         }

         @SerializedName("items")
         private List<Item> items;

         public List<Item> getItems() {
             return items;
         }
     }

     @SerializedName("collection")
     private Collection collection;

     public Collection getCollection() {
        return collection;
    }
}
