package com.example.mycat;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;

import com.example.mycat.CatBreedAdapter;
import com.example.mycat.FetchCatBreedsTask;
import com.example.mycat.R;

import java.util.ArrayList;

public class SearchActivity extends AppCompatActivity {

    private RecyclerView searchRecyclerView;
    private CatBreedAdapter catBreedAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        searchRecyclerView = findViewById(R.id.search_recycler_view);
        searchRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        catBreedAdapter = new CatBreedAdapter(new ArrayList<CatBreed>(), this);
        searchRecyclerView.setAdapter(catBreedAdapter);

        EditText searchInput = findViewById(R.id.search_input);
        searchInput.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                catBreedAdapter.getFilter().filter(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });

        new FetchCatBreedsTask(catBreedAdapter, SearchActivity.this).execute();
    }
}
