package com.example.raghadtaleb.project6_newsapp;

import android.content.Context;
import android.support.v4.content.AsyncTaskLoader;
import android.util.Log;

import org.json.JSONException;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;


/**
 * Created by raghadtaleb on 13/01/2018.
 */

public class Loader extends AsyncTaskLoader<List<JSONadapter>> {
    private String StringwebUrl;

    public Loader(Context context, String url) {
        super(context);
        this.StringwebUrl = url;

    }

    @Override
    protected void onStartLoading() {
        forceLoad();
    }

    @Override
    public List<JSONadapter> loadInBackground() {

        JSONadapter jsonInfo = null;
        List<JSONadapter> news = null;
        try {
            news = Result.GetURLData(makeURL(StringwebUrl));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return news;
    }

    public URL makeURL(String url) {
        URL weburl = null;
        try {
            weburl = new URL(url);
        } catch (MalformedURLException e) {
            Log.e("Loader", e.getMessage());
        }
        return weburl;
    }
}