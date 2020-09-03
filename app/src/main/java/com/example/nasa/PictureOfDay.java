package com.example.nasa;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.MediaController;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

import com.google.android.youtube.player.YouTubeBaseActivity;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerView;
import com.squareup.picasso.Picasso;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class PictureOfDay extends YouTubeBaseActivity {

    private static final String TAG = "PictureOfDay";
    private String date;
    private ImageView picOfDay;
    private TextView textViewTitle,textViewDesc;
    private YouTubePlayerView youTubePlayerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_picture_of_day);
        Intent intent = getIntent();
        date = intent.getStringExtra(MainActivity.DATE);
        textViewTitle = (TextView) findViewById(R.id.textViewTitle);
        textViewDesc = (TextView) findViewById(R.id.textViewDescription);
        textViewDesc.setMovementMethod(new ScrollingMovementMethod());
        youTubePlayerView = (YouTubePlayerView) findViewById(R.id.youTubeView);
        picOfDay = (ImageView) findViewById(R.id.imageViewPOD);
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://api.nasa.gov/planetary/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        NasaAPI nasaAPI = retrofit.create(NasaAPI.class);
        Call<APOD> call = nasaAPI.getAPOD(date, true, MainActivity.API_KEY);
        call.enqueue(new Callback<APOD>() {
            @Override
            public void onResponse(Call<APOD> call, Response<APOD> response) {
                if (!response.isSuccessful()) {
                    Log.d(TAG, "onResponse: " + response.code());
                    return;
                }
                APOD apod = response.body();
                textViewTitle.setText(apod.getTitle());
                textViewDesc.setText(apod.getExplanation());
                if (apod.getMediaType().compareToIgnoreCase("image") == 0) {
                    picOfDay.setVisibility(View.VISIBLE);
                    youTubePlayerView.setVisibility(View.INVISIBLE);
                    if (!apod.getHdUrl().isEmpty())
                        Picasso.with(getApplicationContext()).load(apod.getHdUrl()).fit().into(picOfDay);
                    else
                        Picasso.with(getApplicationContext()).load(apod.getUrl()).fit().into(picOfDay);
                } else {
                    picOfDay.setVisibility(View.INVISIBLE);
                    youTubePlayerView.setVisibility(View.VISIBLE);
                    playVideo(apod.getUrl());
                }
            }

            @Override
            public void onFailure(Call<APOD> call, Throwable t) {
                Log.d(TAG, "onFailure: Throwable - " + t.getMessage());
            }
        });
    }

    public void playVideo(final String url){

        YouTubePlayer.OnInitializedListener onInitializedListener = new YouTubePlayer.OnInitializedListener() {
            @Override
            public void onInitializationSuccess(YouTubePlayer.Provider provider, YouTubePlayer youTubePlayer, boolean b) {
                youTubePlayer.loadVideo(url.substring(30,41));
            }

            @Override
            public void onInitializationFailure(YouTubePlayer.Provider provider, YouTubeInitializationResult youTubeInitializationResult) {
                Log.d(TAG, "onInitializationFailure: ");
            }
        };
        youTubePlayerView.initialize(YouTubeConfig.getApiKey(), onInitializedListener);
    }
}