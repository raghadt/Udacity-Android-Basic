package com.example.raghadtaleb.project5_guidtour;

import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import android.widget.TextView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private Pager PagerAdapter;
    static ArrayList<Resources> resourcesArray=new ArrayList<>();
    private ViewPager mViewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        resourcesArray.add(new Resources("Northen Jeddah Cournich","Beach"));
        resourcesArray.add(new Resources("Southern Jeddah Cournich","Beach"));



        resourcesArray.add(new Resources("RedSea Mall","Malls"));
        resourcesArray.add(new Resources("Mall of Arab","Malls"));

        resourcesArray.add(new Resources("Rosopomodoro","Cuisines"));
        resourcesArray.add(new Resources("PF chang","Cuisines"));

        resourcesArray.add(new Resources("Medd Cafe","Cafes"));
        resourcesArray.add(new Resources("Starbuck","Cafes"));

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
