package com.example.raghadtaleb.project5_guidtour;


import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import java.util.ArrayList;

/**
 * Created by raghadtaleb on 27/12/2017.
 */

public class Cafes extends Fragment {
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View cafes = inflater.inflate(R.layout.cafes, container, false);
        AddListView(container.getContext(), cafes);
        return cafes;
    }

    public void AddListView(Context context, View view) {

        ArrayList<Resources> cafesList = new ArrayList<>();

        for (int i = 0; i < MainActivity.resourcesArray.size(); i++) {
            if (MainActivity.resourcesArray.get(i).getResourceType().equals("Cafes")) {
                cafesList.add(MainActivity.resourcesArray.get(i));
            }
        }
        ListViewAdapter adapter = new ListViewAdapter(context, cafesList);

        ListView listView = view.findViewById(R.id.ResourceListview);

        listView.setAdapter(adapter);


    }
}
