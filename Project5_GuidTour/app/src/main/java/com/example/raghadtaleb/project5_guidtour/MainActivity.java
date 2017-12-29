package com.example.raghadtaleb.project5_guidtour;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    static ArrayList<Resources> resourcesArray = new ArrayList<>();
    private Pager PagerAdapter;
    private ViewPager mViewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        resourcesArray.add(new Resources("Northen Jeddah Cournich", "Beach", R.drawable.courn));
        resourcesArray.add(new Resources("Southern Jeddah Cournich", "Beach", R.drawable.scour));


        resourcesArray.add(new Resources("RedSea Mall", "Malls", R.drawable.resea));
        resourcesArray.add(new Resources("Mall of Arab", "Malls", R.drawable.marab));

        resourcesArray.add(new Resources("Rosopomodoro", "Cuisines", R.drawable.roso));
        resourcesArray.add(new Resources("PF chang", "Cuisines", R.drawable.pfc));

        resourcesArray.add(new Resources("Medd Cafe", "Cafes", R.drawable.medd));
        resourcesArray.add(new Resources("Starbuck", "Cafes", R.drawable.starbucks));

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        TabLayout tabLayout = findViewById(R.id.tabs);
        PagerAdapter = new Pager(getSupportFragmentManager());
        mViewPager = findViewById(R.id.container);
        mViewPager.setAdapter(PagerAdapter);

        mViewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.addOnTabSelectedListener(new TabLayout.ViewPagerOnTabSelectedListener(mViewPager));

    }

}