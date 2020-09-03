package com.example.nasa;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface NasaImageAndVideoAPI {

    @GET("search")
    Call<ImageAndVideoCollection> getItems(@Query("q") String q);
}
