package com.example.raghadtaleb.project6_newsapp;

/**
 * Created by raghadtaleb on 13/01/2018.
 */

public class JSONadapter {

    String title;
    String section_name;
    String author_name;
    String date;
    String webUrl;

    public JSONadapter(String title, String section_name, String author_name, String date, String webUrl) {
        this.title = title;
        this.section_name = section_name;
        this.author_name = author_name;
        this.date = date;
        this.webUrl = webUrl;
    }

    public String getWebUrl() {
        return webUrl;
    }

    public String getTitle() {
        return title;
    }

    public String getSection_name() {
        return section_name;
    }

    public String getAuthor_name() {
        return author_name;
    }

    public String getDate() {
        return date;
    }
}
