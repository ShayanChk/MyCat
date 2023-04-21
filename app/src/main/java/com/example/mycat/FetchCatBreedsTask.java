package com.example.mycat;

import android.content.Context;
import android.os.AsyncTask;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class FetchCatBreedsTask extends AsyncTask<Void, Void, List<CatBreed>> {
    private CatBreedAdapter catBreedAdapter;
    private Context context;


    public FetchCatBreedsTask(CatBreedAdapter catBreedAdapter, Context context) {
        this.catBreedAdapter = catBreedAdapter;
        this.context = context;

    }

    @Override
    protected List<CatBreed> doInBackground(Void... voids) {
        try {
            URL url = new URL("https://catfact.ninja/breeds");
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");

            if (connection.getResponseCode() == HttpURLConnection.HTTP_OK) {
                InputStreamReader inputStreamReader = new InputStreamReader(connection.getInputStream());
                BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

                StringBuilder response = new StringBuilder();
                String line;

                while ((line = bufferedReader.readLine()) != null) {
                    response.append(line);
                }

                JSONObject jsonResponse = new JSONObject(response.toString());
                JSONArray breedsArray = jsonResponse.getJSONArray("data");

                List<CatBreed> catBreeds = new ArrayList<>();

                for (int i = 0; i < breedsArray.length(); i++) {
                    JSONObject breedObject = breedsArray.getJSONObject(i);
                    String breed = breedObject.getString("breed");
                    String country = breedObject.getString("country");
                    String origin = breedObject.getString("origin");
                    String coat = breedObject.getString("coat");
                    String pattern = breedObject.getString("pattern");

                    CatBreed catBreed = new CatBreed(breed, country, origin, coat, pattern);
                    catBreeds.add(catBreed);
                }

                return catBreeds;
            }

        } catch (IOException | JSONException e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    protected void onPostExecute(List<CatBreed> catBreeds) {
        if (catBreeds != null) {
            catBreedAdapter.setCatBreeds(catBreeds);
        } else {
            Toast.makeText(context, "Failed to fetch data", Toast.LENGTH_SHORT).show();
        }
    }
}
