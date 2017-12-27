package com.example.raghadtaleb.project5_guidtour;


import android.content.Context;
import android.content.res.Resources;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import java.util.ArrayList;

/**
 * Created by raghadtaleb on 27/12/2017.
 */

public class ListViewAdapter extends BaseAdapter {
     Context Context;
    ArrayList<Resources> resourcesList;

    public ListViewAdapter(Context c, ArrayList<Resources> AttractionList) {
        Context = c;
        this.resourcesList=AttractionList;


    }

    @Override
    public int getCount() {
        return resourcesList.size();
    }

    @Override
    public Resources getItem(int position) {
        return resourcesList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view= LayoutInflater.from(Context).inflate(R.layout.listitem,parent,false);

        TextView AttractionName=view.findViewById(R.id.name);
        TextView AttractionInfo=view.findViewById(R.id.type);

        AttractionName.setText(getItem(position).getName());
        AttractionInfo.setText(getItem(position).getResourceType());

        return view;
    }
}
