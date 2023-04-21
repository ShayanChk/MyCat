package com.example.mycat;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private CatBreedAdapter catBreedAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        catBreedAdapter = new CatBreedAdapter(new ArrayList<>(), MainActivity.this);
        recyclerView.setAdapter(catBreedAdapter);

        new FetchCatBreedsTask(catBreedAdapter, MainActivity.this).execute();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_random_cat_fact) {
            Intent randomCatFactIntent = new Intent(this, RandomCatFactActivity.class);
            startActivity(randomCatFactIntent);
            return true;
        } else if (id == R.id.action_search) {
            Intent searchIntent = new Intent(this, SearchActivity.class);
            startActivity(searchIntent);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
