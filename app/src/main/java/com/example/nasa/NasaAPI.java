package com.example.nasa;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface NasaAPI {

    @GET("apod")
    Call<APOD> getAPOD(
            @Query("date") String date,
            @Query("hd") boolean hd,
            @Query("api_key") String key);
}
