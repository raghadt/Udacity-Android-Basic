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

/**
 * Created by raghadtaleb on 13/01/2018.
 */

public class Result {

    static String name;
    private static String LOG_TAG = Result.class.getSimpleName();

    private Result() {
    }

    public static JSONadapter extractResultFromJson(String JsonString) throws JSONException {
        JSONadapter jsonInformation = null;

        //TODO: get the json information and fill the object

        JSONObject root = new JSONObject(JsonString);
        JSONObject response = root.getJSONObject("response");
        JSONArray result = response.getJSONArray("result");
        int m = result.length();
        ArrayList<String> resultArray = new ArrayList<>();
        String title="", sectionName="";
        if (m > 0) {
            for (int i = 0; i < m; i++) {
                JSONObject j = result.getJSONObject(i);
                title = j.getString("webTitle");
                resultArray.add(title);

                sectionName = j.getString("sectionName");
                resultArray.add(sectionName);
            }
        }
        jsonInformation = new JSONadapter(title, sectionName);
        return jsonInformation;

    }


    public static JSONadapter GetURLData(URL requestUrl) throws JSONException {

        String jsonResponse = null;
        try {
            jsonResponse = makeHttpRequest(requestUrl);
        } catch (IOException e) {
            Log.e(LOG_TAG, e.getMessage());
        }
        JSONadapter jsonInformation = extractResultFromJson(jsonResponse);

        return jsonInformation;
    }

    private static String makeHttpRequest(URL url) throws IOException {
        String jsonResponse = "";

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
                jsonResponse = readFromStream(inputStream);
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
        return jsonResponse;
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
