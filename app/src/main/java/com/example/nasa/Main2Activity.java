package com.example.nasa;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Main2Activity extends AppCompatActivity {

    private static final String TAG = "Main2Activity.LOG";
    private NasaImageAndVideoAPI nasaImageAPI;
    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    private ImageAndVideoCollection imageAndVideoCollection;
    private SearchView searchView;
    private List<Item> itemList;
    private List<Data> dataList;
    private List<String> titles;
    private List<String> descriptions;
    private List<CardItem> cardList;
    private List<String> nasaIDs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        titles = new ArrayList<>();
        descriptions = new ArrayList<>();
        cardList = new ArrayList<>();
        nasaIDs = new ArrayList<>();
        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        layoutManager = new LinearLayoutManager(this);
       /* CardItem cardItem = new CardItem("Hi","Hello!!");
        cardList.add(cardItem);
        cardList.add(cardItem);
        displaySearchResults();*/
    }


    public void getData(String query){
        final GsonBuilder gsonBuilder = new GsonBuilder();
        final Gson gson = gsonBuilder.create();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://images-api.nasa.gov/")
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();
        nasaImageAPI = retrofit.create(NasaImageAndVideoAPI.class);
        Call<ImageAndVideoCollection> call = nasaImageAPI.getItems(query);
        call.enqueue(new Callback<ImageAndVideoCollection>() {
            @Override
            public void onResponse(Call<ImageAndVideoCollection> call, Response<ImageAndVideoCollection> response) {
                if(!response.isSuccessful()){
                    Log.d(TAG, "onResponse: "+response.code());
                    return;
                }
                titles.clear();
                descriptions.clear();
                cardList.clear();
                nasaIDs.clear();
                imageAndVideoCollection = response.body();
                itemList = new ArrayList<Item>(imageAndVideoCollection.getCollection().getItems());
                for(Item item : itemList){
                    dataList = new ArrayList<>(item.getData());
                    for(Data data : dataList){
                        titles.add(data.getTitle());
                        nasaIDs.add(data.getNasaID());
                        if(data.getDescription()!=null)
                            descriptions.add(data.getDescription());
                        else
                            descriptions.add(" ");
                        cardList.add(new CardItem(titles.get(titles.size()-1), descriptions.get(descriptions.size()-1)));
                    }
                }
            }

            @Override
            public void onFailure(Call<ImageAndVideoCollection> call, Throwable t) {
                Log.d(TAG, "onFailure: ",t);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.search_menu, menu);
        MenuItem item = menu.findItem(R.id.search_action);
        searchView = (SearchView) item.getActionView();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                getData(newText);
                displaySearchResults();
                return false;
            }
        });
        return true;
    }

    private void displaySearchResults(){
        MyAdapter myAdapter = new MyAdapter(this, cardList);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(myAdapter);

        myAdapter.setOnItemClickListener(new MyAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                Intent intent = new Intent(getApplicationContext(), NasaIdImage.class);
                intent.putExtra("TITLE",titles.get(position));
                intent.putExtra("ID",nasaIDs.get(position));
                startActivity(intent);
            }
        });
    }

}