package com.example.raghadtaleb.project6_newsapp;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by raghadtaleb on 13/01/2018.
 */

public class Result {

    private static String LOG_TAG = Result.class.getSimpleName();

    private Result() {
    }

    public static List<JSONadapter> extractResultFromJson(String JsonString) throws JSONException {
        JSONadapter jsonInformation = null;
        String title = "", webPublicationDate = "", webUrl = "", sectionName = "", fName = "", lName = "", authorName = "Anon";
        ArrayList<JSONadapter> newsArray = new ArrayList<>();


        JSONObject root = new JSONObject(JsonString);
        JSONObject response = root.getJSONObject("response");
        JSONArray result = response.getJSONArray("results");

        int m = result.length();
        if (m > 0) {
            for (int i = 0; i < m; i++) {
                JSONObject jobject = result.getJSONObject(i);
                title = jobject.getString("webTitle");
                sectionName = jobject.getString("sectionName");
                webUrl = jobject.getString("webUrl");
                webPublicationDate = jobject.getString("webPublicationDate");
                JSONArray tags = jobject.getJSONArray("tags");

                for (int j = 0; j < tags.length(); j++) {

                    JSONObject jObjectTags = tags.getJSONObject(j);
                    fName = jObjectTags.getString("firstName");
                    lName = jObjectTags.getString("lastName");

                    if (fName.equals("")) {
                        authorName = "By: " + lName;
                    } else if (lName.equals("")) {
                        authorName = "By: " + fName;
                    } else {
                        authorName = "By: " + fName + " " + lName;
                    }
                }

                jsonInformation = new JSONadapter(title, sectionName, authorName, webPublicationDate, webUrl);
                newsArray.add(jsonInformation);
            }
        }
        return newsArray;
    }


    public static List<JSONadapter> GetURLData(URL requestUrl) throws JSONException {

        String jsonResponse = "";
        List<JSONadapter> news = null;

        try {
            jsonResponse = makeHttpRequest(requestUrl);
        } catch (IOException e) {
            Log.e(LOG_TAG, e.getMessage());
        }
        news = extractResultFromJson(jsonResponse);
        return news;
    }

    private static String makeHttpRequest(URL url) throws IOException {
        String jsonResult = "";
        HttpURLConnection urlConnection = null;
        InputStream inputStream = null;

        try {
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setReadTimeout(10000);
            urlConnection.setConnectTimeout(15000);
            urlConnection.setRequestMethod("GET");
            urlConnection.connect();

            if (urlConnection.getResponseCode() == 200) {
                inputStream = urlConnection.getInputStream();
                jsonResult = readFromStream(inputStream);
            }
        } catch (IOException e) {
            Log.e(LOG_TAG, e.getMessage());
        } finally {
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
            if (inputStream != null) {
                inputStream.close();
            }
        }
        return jsonResult;
    }

    private static String readFromStream(InputStream inputStream) throws IOException {

        StringBuilder output = new StringBuilder();
        if (inputStream != null) {
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream, Charset.forName("UTF-8"));
            BufferedReader reader = new BufferedReader(inputStreamReader);
            String line = reader.readLine();

            while (line != null) {
                output.append(line);
                line = reader.readLine();
            }
        }
        return output.toString();
    }

}
