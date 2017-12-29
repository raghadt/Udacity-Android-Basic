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

public class Cuisines extends Fragment {
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View cuisines = inflater.inflate(R.layout.cuisines, container, false);
        AddListView(container.getContext(), cuisines);
        return cuisines;
    }

    public void AddListView(Context context, View view) {

        ArrayList<Resources> cuisinesList = new ArrayList<>();
        for (int i = 0; i < MainActivity.resourcesArray.size(); i++) {
            if (MainActivity.resourcesArray.get(i).getResourceType().equals("Cuisines")) {
                cuisinesList.add(MainActivity.resourcesArray.get(i));
            }
        }
        ListViewAdapter adapter = new ListViewAdapter(context, cuisinesList);

        ListView listView = view.findViewById(R.id.ResourceListview);

        listView.setAdapter(adapter);


    }
}
