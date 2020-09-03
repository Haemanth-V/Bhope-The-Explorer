package com.example.nasa;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface MediaAssetManifest {

    @GET("asset/{nasa_id}")
    Call<NasaAssetIDCollection> getAsset(@Path("nasa_id") String id);
}
