package com.example.raghadtaleb.project6_newsapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by raghadtaleb on 15/01/2018.
 */

public class NewsAdapter extends ArrayAdapter<JSONadapter> {

    public NewsAdapter(Context context) {
        super(context, -1, new ArrayList<JSONadapter>());
    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {
        if (view == null) {
            view = LayoutInflater.from(getContext()).inflate(R.layout.list_items, parent, false);
        }
        TextView title = view.findViewById(R.id.title);
        TextView author = view.findViewById(R.id.author);
        TextView date = view.findViewById(R.id.date);
        TextView section = view.findViewById(R.id.section_name);

        JSONadapter currentNews = getItem(position);

        title.setText(currentNews.getTitle());
        author.setText(currentNews.getAuthor_name());
        date.setText(currentNews.getDate());
        section.setText(currentNews.getSection_name());

        return view;
    }
}
