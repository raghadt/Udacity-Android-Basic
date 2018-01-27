package com.example.raghadtaleb.project6_newsapp;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.LoaderManager;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

public class MainActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<List<JSONadapter>>, SharedPreferences.OnSharedPreferenceChangeListener {
    private static final String LOG_TAG = MainActivity.class.getSimpleName();
//        static String ENDPOINT = "http://content.guardianapis.com/search?q=debate&tag=politics/politics&from-date=2014-01-01&api-key=test&show-tags=contributor";

    static String ENDPOINT = "https://content.guardianapis.com/search?q=";
    TextView info;
    private NewsAdapter adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        info = findViewById(R.id.text);


        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo ni = cm.getActiveNetworkInfo();
        ListView listView = findViewById(R.id.list_view);

        if (ni != null && ni.isConnected()) {
            adapter = new NewsAdapter(this);
            listView.setAdapter(adapter);

            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                    JSONadapter news = adapter.getItem(i);
                    Uri newsUri = Uri.parse(news.getWebUrl());

                    Log.d(LOG_TAG, newsUri.toString());

                    if (news.getWebUrl() == null || TextUtils.isEmpty(news.getWebUrl())) {
                        Toast.makeText(MainActivity.this, "No Link", Toast.LENGTH_SHORT).show();
                    } else {
                        Intent intent = new Intent(Intent.ACTION_VIEW, newsUri);
                        startActivity(intent);
                    }
                }
            });

            getSupportLoaderManager().initLoader(1, null, MainActivity.this).forceLoad();


            //////////------------------------
            SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
            sharedPreferences.registerOnSharedPreferenceChangeListener(this);
            //-------------------

        } else {
            listView.setEmptyView(info);
            info.setText(":( \n Make sure you're connected to the internet.");

        }
    }

    //////-----------------------------------------------

    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {


        if (key.contains((getString(R.string.settings_category_key)))) {
            //The onCreateLoader method will read the preferences
            getSupportLoaderManager().restartLoader(1, null, MainActivity.this);
        } else if (key.contains(getString(R.string.order_by_key))) {
            //The onCreateLoader method will read the preferences
            getSupportLoaderManager().restartLoader(1, null, MainActivity.this);
        }
    }
    //------------------------------------------------------

    public android.support.v4.content.Loader onCreateLoader(int id, Bundle args) {

        SharedPreferences sharedPrefs = PreferenceManager.getDefaultSharedPreferences(this);

        String category = sharedPrefs.getString(
                getString(R.string.settings_category_key), getString(R.string.settings_category_default));


        String orderBy = sharedPrefs.getString(
                getString(R.string.order_by_key), getString(R.string.order_by_default));

        Uri baseUri = Uri.parse(ENDPOINT);
        Uri.Builder uriBuilder = baseUri.buildUpon();
        uriBuilder.appendQueryParameter("", category);
        uriBuilder.appendQueryParameter("order-by", orderBy);
        uriBuilder.appendQueryParameter("show-tags", "contributor");
        uriBuilder.appendQueryParameter("page-size", "20");
        uriBuilder.appendQueryParameter("api-key", "89867c4d-5bcd-46d1-8205-d34fedd9d876");

        String fin = uriBuilder.toString().replace("&=", "");
//        Log.d("MainActivity", fin);

        return new Loader(this, fin);

    }


    public void onLoadFinished(android.support.v4.content.Loader<List<JSONadapter>> loader, List<JSONadapter> data) {
        adapter.setNotifyOnChange(false);
        adapter.clear();
        adapter.setNotifyOnChange(true);
        adapter.addAll(data);
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        PreferenceManager.getDefaultSharedPreferences(this).unregisterOnSharedPreferenceChangeListener(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_activity, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.menu_settings) {
            Intent settingsIntent = new Intent(this, Settings.class);
            startActivity(settingsIntent);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void onLoaderReset(android.support.v4.content.Loader<List<JSONadapter>> loader) {

    }

}