package com.example.raghadtaleb.project6_newsapp;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v4.app.LoaderManager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<List<JSONadapter>> {
    static String ENDPOINT = "http://content.guardianapis.com/search?q=debate&tag=politics/politics&from-date=2014-01-01&api-key=test&show-tags=contributor";
    TextView info, title, author, section_name, date;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        info=findViewById(R.id.text);
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo ni = cm.getActiveNetworkInfo();
        if(ni!=null && ni.isConnected()){

            getSupportLoaderManager().initLoader(1, null, MainActivity.this).forceLoad();
        } else {
            info.setText("Make sure you're connected to the internet.");
        }
    }

    public android.support.v4.content.Loader onCreateLoader(int id, Bundle args) {
        return new Loader(MainActivity.this, MainActivity.ENDPOINT);
    }


    public void onLoadFinished(android.support.v4.content.Loader<List<JSONadapter>> loader, List<JSONadapter> data) {

        title.setText(data.getTitle());
        author.setText(data.getAuthor_name());
        date.setText(data.getDate());
        section_name.setText(data.getSection_name());

    }


    public void onLoaderReset(android.support.v4.content.Loader<List<JSONadapter>> loader) {

    }

}