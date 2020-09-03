package com.example.nasa;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class NasaIdImage extends AppCompatActivity {

    private static final String TAG = "NasaIdImage : Log";
    private ImageView imageView;
    private TextView textViewTitle1;
    private String id, title;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nasa_id_image);
        Intent intent = getIntent();
        title = intent.getStringExtra("TITLE");
        id = intent.getStringExtra("ID");
        Log.d(TAG, "NASA ID "+id);
        textViewTitle1 = (TextView) findViewById(R.id.textViewTitle1);
        imageView = (ImageView) findViewById(R.id.imageView);
        textViewTitle1.setText(title);
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://images-api.nasa.gov/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        MediaAssetManifest mediaAssetManifest = retrofit.create(MediaAssetManifest.class);
        Call<NasaAssetIDCollection> call = mediaAssetManifest.getAsset(id);
        call.enqueue(new Callback<NasaAssetIDCollection>() {
            @Override
            public void onResponse(Call<NasaAssetIDCollection> call, Response<NasaAssetIDCollection> response) {
                if (!response.isSuccessful()) {
                    Log.d(TAG, "onResponse: " + response.code());
                    return;

                }
                NasaAssetIDCollection idCollection = response.body();
                textViewTitle1.setText(title);
                int flag=0;
                for (NasaAssetIDCollection.Collection.Item item : idCollection.getCollection().getItems()) {
                    if (item.getUrl().endsWith(".jpg")) {
                        Picasso.with(NasaIdImage.this).load(item.getUrl().replace("http","https")).fit().into(imageView);
                        Log.d(TAG, "onResponse: "+item.getUrl());
                        flag++;
                        break;
                    }
                    if(flag!=0) {
                        String msg = "Image not available!";
                        Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_LONG).show();
                    }
                }
            }

            @Override
            public void onFailure(Call<NasaAssetIDCollection> call, Throwable t) {
                Log.d(TAG, "onFailure: Throwable - " + t.getMessage());
            }
        });

    }
}