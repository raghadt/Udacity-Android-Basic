package com.example.raghadtaleb.project6_newsapp;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.LoaderManager;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

public class MainActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<List<JSONadapter>> {
    private static final String LOG_TAG = MainActivity.class.getSimpleName();
    static String ENDPOINT = "http://content.guardianapis.com/search?q=debate&tag=politics/politics&from-date=2014-01-01&api-key=test&show-tags=contributor";
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
        } else {
            listView.setEmptyView(info);
            info.setText(":( \n Make sure you're connected to the internet.");

        }
    }

    public android.support.v4.content.Loader onCreateLoader(int id, Bundle args) {
        return new Loader(MainActivity.this, MainActivity.ENDPOINT);
    }


    public void onLoadFinished(android.support.v4.content.Loader<List<JSONadapter>> loader, List<JSONadapter> data) {
        adapter.setNotifyOnChange(false);
        adapter.clear();
        adapter.setNotifyOnChange(true);
        adapter.addAll(data);
    }

    public void onLoaderReset(android.support.v4.content.Loader<List<JSONadapter>> loader) {

    }

}