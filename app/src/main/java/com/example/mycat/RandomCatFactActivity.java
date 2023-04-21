package com.example.mycat;

import android.os.AsyncTask;
import android.os.Bundle;
//import androidx.appcompat.app.AppCompatActivityimport android.view.View;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class RandomCatFactActivity extends AppCompatActivity {

    private TextView tvRandomCatFact;
    private Button btnFetchRandomFact;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_random_cat_fact);

        tvRandomCatFact = findViewById(R.id.tv_random_cat_fact);
        btnFetchRandomFact = findViewById(R.id.btn_fetch_random_fact);

        btnFetchRandomFact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fetchRandomCatFact();
            }
        });
    }

    private void fetchRandomCatFact() {
        new FetchRandomCatFactTask().execute();
    }

    private class FetchRandomCatFactTask extends AsyncTask<Void, Void, String> {

        private static final String RANDOM_CAT_FACT_API_URL = "https://catfact.ninja/fact";

        @Override
        protected String doInBackground(Void... voids) {
            try {
                URL url = new URL(RANDOM_CAT_FACT_API_URL);
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.setRequestMethod("GET");
                connection.connect();

                int responseCode = connection.getResponseCode();
                if (responseCode != HttpURLConnection.HTTP_OK) {
                    return null;
                }

                InputStream inputStream = connection.getInputStream();
                InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
                BufferedReader reader = new BufferedReader(inputStreamReader);

                StringBuilder jsonResult = new StringBuilder();
                String line;
                while ((line = reader.readLine()) != null) {
                    jsonResult.append(line);
                }

                JSONObject jsonObject = new JSONObject(jsonResult.toString());
                String randomFact = jsonObject.getString("fact");

                return randomFact;
            } catch (IOException | JSONException e) {
                e.printStackTrace();
            }

            return null;
        }

        @Override
        protected void onPostExecute(String randomFact) {
            if (randomFact != null) {
                tvRandomCatFact.setText(randomFact);
            } else {
                Toast.makeText(RandomCatFactActivity.this, "Failed to fetch random cat fact.", Toast.LENGTH_SHORT).show();
            }
        }
    }
}
