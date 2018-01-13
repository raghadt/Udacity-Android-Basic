package com.example.raghadtaleb.project6_newsapp;

/**
 * Created by raghadtaleb on 13/01/2018.
 */

public class JSONadapter {

    String title;
    String section_name;
    String author_name;
    String date;

    public JSONadapter(String title, String section_name, String author_name, String date) {
        this.title = title;
        this.section_name = section_name;
        this.author_name = author_name;
        this.date = date;
    }

    public JSONadapter(String title, String section_name) {
        this.title = title;
        this.section_name = section_name;
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
