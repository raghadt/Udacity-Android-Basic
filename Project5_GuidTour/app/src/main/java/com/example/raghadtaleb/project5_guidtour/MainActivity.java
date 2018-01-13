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



        resourcesArray.add(new Resources(getString(R.string.northc),getString(R.string.beach), R.drawable.courn));
        resourcesArray.add(new Resources(getString(R.string.southc), getString(R.string.beach), R.drawable.scour));


        resourcesArray.add(new Resources(getString(R.string.redsea),  getString(R.string.malls), R.drawable.resea));
        resourcesArray.add(new Resources(getString(R.string.arabmall),  getString(R.string.malls), R.drawable.marab));

        resourcesArray.add(new Resources(getString(R.string.rosopomodoro), getString(R.string.cuisines), R.drawable.roso));
        resourcesArray.add(new Resources(getString(R.string.pfc), getString(R.string.cuisines), R.drawable.pfc));

        resourcesArray.add(new Resources(getString(R.string.Medd), getString(R.string.cafes), R.drawable.medd));
        resourcesArray.add(new Resources(getString(R.string.Starbucks), getString(R.string.cafes), R.drawable.starbucks));

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