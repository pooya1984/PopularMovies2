package com.example.pooya.popularmovies;

import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.app.NavUtils;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.pooya.popularmovies.adapter.ViewPagerAdapter;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;

public class DetailActivity extends AppCompatActivity {

    public static String ur = "http://image.tmdb.org/t/p/w500";
    private static String url = "https://api.themoviedb.org/3/movie/550";
    private static String urls = "https://api.themoviedb.org/3/movie/popular";
    private static String urlr = "https://api.themoviedb.org/3/movie/top_rated";
    final static String PARAM_QUERY = "api_key";
    private static final String API_KEY = BuildConfig.API_KEY;
    String id;
    int help;
    ImageView imageView;
    Intent intent;
    TextView textView2, textView3, textView5, textView6;
    private ProgressBar mLoadingIndicator;
    String textEntered, title, vote_count, overview, poster_path, release_date, vote_average;
    ActionBar mActionBar;
    ActionBar m2ActionBar;
    ViewPager mPager;
    ActionBar.Tab tab;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        mActionBar = getSupportActionBar();
        mActionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
        mPager = findViewById(R.id.pager);
        FragmentManager fm = getSupportFragmentManager();
        ViewPager.SimpleOnPageChangeListener ViewPagerListener = new ViewPager.SimpleOnPageChangeListener() {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                // Find the ViewPager Position
                mActionBar.setSelectedNavigationItem(position);
            }
        };

        mPager.setOnPageChangeListener(ViewPagerListener);
        // Locate the adapter class called ViewPagerAdapter.java
        ViewPagerAdapter viewpageradapter = new ViewPagerAdapter(fm);
        // Set the View Pager Adapter into ViewPager
        mPager.setAdapter(viewpageradapter);

        // Capture tab button clicks
        ActionBar.TabListener tabListener = new ActionBar.TabListener() {

            @Override
            public void onTabSelected(ActionBar.Tab tab, FragmentTransaction ft) {
                // Pass the position on tab click to ViewPager
                mPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(ActionBar.Tab tab, FragmentTransaction ft) {
                // TODO Auto-generated method stub

            }

            @Override
            public void onTabReselected(ActionBar.Tab tab, FragmentTransaction ft) {
                // TODO Auto-generated method stub
            }
        };

        // Create first Tab
        tab = mActionBar.newTab().setText("Tab1").setTabListener(tabListener);
        mActionBar.addTab(tab);

        // Create second Tab
        tab = mActionBar.newTab().setText("Tab2").setTabListener(tabListener);
        mActionBar.addTab(tab);

        // Create third Tab
        tab = mActionBar.newTab().setText("Tab3").setTabListener(tabListener);
        mActionBar.addTab(tab);


        textView2 = findViewById(R.id.titles);
        textView3 = findViewById(R.id.year);
        textView5 = findViewById(R.id.rating);
        textView6 = findViewById(R.id.detail);
        imageView = findViewById(R.id.images);
        mLoadingIndicator = findViewById(R.id.pb_loading_indicator);



        m2ActionBar = this.getSupportActionBar();
        if (m2ActionBar != null) {
            m2ActionBar.setDisplayHomeAsUpEnabled(true);
        }

        intent = getIntent();
        Bundle extras = intent.getExtras();
        textEntered = extras.getString("ide");
        help = extras.getInt("democheck");
        if (help < 1) {
            URL query = uriBuilders(urls);
            new queryTasks().execute(query);
        } else {
            URL query = uriBuilders(urlr);
            new queryTasks().execute(query);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        // When the home button is pressed, take the user back to the VisualizerActivity
        if (id == android.R.id.home) {
            NavUtils.navigateUpFromSameTask(this);
        }
        return super.onOptionsItemSelected(item);
    }

    public URL uriBuilders(String uri) {
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

    //public void open_web(String url) {
        /*
         * We wanted to demonstrate the Uri.parse method because its usage occurs frequently. You
         * could have just as easily passed in a Uri as the parameter of this method.

    imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Uri webpage = Uri.parse(url);
                Log.e("url", "->"+webpage);
                Intent intent = new Intent(Intent.ACTION_VIEW, webpage);
                //startActivity(intent);
                if (intent.resolveActivity(getPackageManager()) != null) {
                    startActivity(intent);
                }
            }
        });

    }*/


    private class queryTasks extends AsyncTask<URL, Void, String> {
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
                        for (int i = 0; i <= contacts.length(); i++) {
                            JSONObject c = contacts.getJSONObject(i);
                            id = c.getString("id");
                            if (textEntered.equals(id)) {
                                release_date = c.getString("release_date");
                                vote_average = c.getString("vote_average");
                                title = c.getString("title");
                                vote_count = c.getString("vote_count");
                                overview = c.getString("overview");
                                poster_path = c.getString("poster_path");
                                i = contacts.length();

                            }

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
            mLoadingIndicator.setVisibility(View.INVISIBLE);

            if (s != null && !s.equals("")) {
                textView2.setText(title);
                textView3.setText(release_date);
                String voting = vote_average + getString(R.string.complete);
                textView5.setText(voting);
                textView6.setText(overview);
                String web = ur + poster_path;
                Picasso.with(getApplicationContext()).load(web).into(imageView);
            }

        }

        @Override
        protected void onPreExecute() {
            mLoadingIndicator.setVisibility(View.VISIBLE);

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
