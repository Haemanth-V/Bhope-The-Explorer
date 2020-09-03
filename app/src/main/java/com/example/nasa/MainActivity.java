package com.example.nasa;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.DialogFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity implements DatePickerDialog.OnDateSetListener {

    public static final String API_KEY = "pBAFVeMPQKsGPcLqAivXNAqj90an7rst4bfbwh1V";
    public static final String DATE = "DATE";
    private static final String TAG = "MainActivity Log";
    private TextView textViewDate;
    private Button buttonPickDate, buttonFetch;
    private String date, lowerLimitDate, todaysDate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        lowerLimitDate = "1995-06-16";
        textViewDate = (TextView) findViewById(R.id.textViewDate);
        buttonPickDate = (Button) findViewById(R.id.buttonDatePicker);
        buttonFetch = (Button) findViewById(R.id.buttonFetch);

    }

    public void openDatePicker(View view){
        DialogFragment datePicker = new DatePickerFragment();
        datePicker.show(getSupportFragmentManager(), "Date Picker");
    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        Calendar calendar = Calendar.getInstance();
        todaysDate = String.format(Locale.getDefault(), "%4d-%02d-%02d",
                calendar.get(Calendar.YEAR) ,calendar.get(Calendar.MONTH)+1,
                calendar.get(Calendar.DAY_OF_MONTH));
        calendar.set(Calendar.YEAR,year);
        calendar.set(Calendar.MONTH, month);
        calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
        date = String.format(Locale.getDefault(),"%4d-%02d-%02d",year,month+1,dayOfMonth);
        String dateText = DateFormat.getDateInstance(DateFormat.FULL).format(calendar.getTime());
        textViewDate.setText(dateText);
    }

    public void fetchPic(View view){
        if(TextUtils.isEmpty(date)){
            String msg = "Pick a Date!";
            Toast.makeText(this,msg,Toast.LENGTH_LONG).show();
            return;
        }
        else if(date.compareTo(lowerLimitDate)<0 || date.compareTo(todaysDate)>0){
            String msg = "Date must be between " + lowerLimitDate + " and " + todaysDate;
            Toast.makeText(this,msg,Toast.LENGTH_LONG).show();
            return;
        }
        Toast.makeText(this,lowerLimitDate + " and " + todaysDate, Toast.LENGTH_LONG).show();
        Intent intent = new Intent(this, PictureOfDay.class);
        intent.putExtra(DATE, date);
        startActivity(intent);
    }
}