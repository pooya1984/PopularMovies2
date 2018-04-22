package com.example.pooya.popularmovies;

import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.example.pooya.popularmovies.adapter.CategorizedMovie;
import com.example.pooya.popularmovies.adapter.FragmentAdapter;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;

import static com.example.pooya.popularmovies.DetailActivity.getResponseFromHttpUrl;

public class FragmentActivity extends AppCompatActivity implements FragmentAdapter.ForecastAdapterOnClickHandler {

    private FragmentAdapter fAdapter;
    private RecyclerView mFragmentList;
    private static String urls = "https://api.themoviedb.org/3/movie/popular";
    private static String urlr = "https://api.themoviedb.org/3/movie/top_rated";
    final static String PARAM_QUERY = "api_key";
    private static final String API_KEY = BuildConfig.API_KEY;
    CategorizedMovie[] categorizedMovies;
    int go = 1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragmenttab1);
        mFragmentList = findViewById(R.id.recycler_tab1);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        mFragmentList.setLayoutManager(layoutManager);
        mFragmentList.setHasFixedSize(true);
        fAdapter = new FragmentAdapter(this);
        mFragmentList.setAdapter(fAdapter);
        goToPop();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int menuItemThatWasSelected = item.getItemId();
        if (menuItemThatWasSelected == R.id.action_sort_rating) {
            go = 0;
            fAdapter.setData(null);
            goToRate();
            return true;
        }
        if (menuItemThatWasSelected == R.id.action_sort_popularity) {
            fAdapter.setData(null);
            go = 1;
            goToPop();
            return true;
        }
        return true;
    }

    public void goToPop() {
        /*mMoviesList.setAdapter(null);
        categorizedMovies = null;*/
        //mAdapter.setData(null);
        URL query = uriBuilder(urls);
        new FragmentActivity.queryTask().execute(query);
    }

    public void goToRate() {
        /*mMoviesList.setAdapter(null);
        categorizedMovies = null;*/
        //mAdapter.setData(null);
        URL query = uriBuilder(urlr);
        new FragmentActivity.queryTask().execute(query);
    }



    public URL uriBuilder(String uri) {
        Uri builtUri = Uri.parse(uri).buildUpon()
                .appendQueryParameter(PARAM_QUERY, API_KEY)
                .build();
        URL url = null;
        try {
            url = new URL(builtUri.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return url;
    }

    @Override
    public void onClick(CategorizedMovie categorizedMovies, int idm) {
        Intent startChildActivityIntent = new Intent(FragmentActivity.this, DetailActivity.class);
        Bundle extras = new Bundle();
        extras.putString("fid", categorizedMovies.ip);
        startChildActivityIntent.putExtras(extras);
        startActivity(startChildActivityIntent);
    }

    private class queryTask extends AsyncTask<URL, Void, String> {
        @Override
        protected String doInBackground(URL... params) {
            URL searchURL = params[0];
            String searchResults = null;
            try {
                searchResults = getResponseFromHttpUrl(searchURL);
                if (searchResults != null) {
                    try {
                        JSONObject jsonObj = new JSONObject(searchResults);
                        JSONArray contacts = jsonObj.getJSONArray("results");
                        categorizedMovies = new CategorizedMovie[contacts.length()];
                        for (int i = 0; i < contacts.length(); i++) {
                            JSONObject c = contacts.getJSONObject(i);
                            String id = c.getString("id");
                            String title = c.getString("title");
                            String vote_count = c.getString("vote_count");
                            String overview = c.getString("overview");
                            String poster_path = c.getString("poster_path");
                            categorizedMovies[i] = new CategorizedMovie(title, vote_count, overview, poster_path, id);
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            return searchResults;
        }

        @Override
        protected void onPostExecute(String s) {
            Log.d("pooya", "cinema");
            if (s != null && !s.equals("")) {
                StaggeredGridLayoutManager layoutManager = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
                mFragmentList.setLayoutManager(layoutManager);
                mFragmentList.setHasFixedSize(true);
                fAdapter.setData(categorizedMovies);
            }
        }

    }


    public static String getResponseFromHttpUrl(URL url) throws IOException {

        HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();

        try {
            InputStream in = urlConnection.getInputStream();
            Scanner scanner = new Scanner(in);
            scanner.useDelimiter("\\A");
            boolean hasInput = scanner.hasNext();
            if (hasInput) {

                return scanner.next();
            } else {

                return null;
            }
        } finally {
            urlConnection.disconnect();
        }
    }
}