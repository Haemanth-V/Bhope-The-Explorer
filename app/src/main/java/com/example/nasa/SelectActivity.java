package com.example.nasa;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class SelectActivity extends AppCompatActivity {

    //private Button buttonAPOD, buttonImageAndVideoLibrary;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select);
    }

    public void apod(View view){
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }


    public void imageAndVideoLibrary(View view){
        Intent intent = new Intent(this, Main2Activity.class);
        startActivity(intent);
    }
}