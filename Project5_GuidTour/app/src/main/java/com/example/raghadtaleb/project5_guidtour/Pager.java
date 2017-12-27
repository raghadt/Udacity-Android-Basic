package com.example.raghadtaleb.project5_guidtour;

/**
 * Created by raghadtaleb on 27/12/2017.
 */

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.ArrayList;

public class Pager extends FragmentPagerAdapter {

    public Pager(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        switch (position){
            case 0:
                Beach beach=new Beach();
                return beach;

            case 1:
                Malls malls=new Malls();
                return malls;

            case 2:
                Cuisines cuisines=new Cuisines();
                return cuisines;

            case 3:
                Cafes cafes=new Cafes();
                return cafes;
        }
        return null;
    }

    @Override
    public int getCount() {
        return 4;
    }
}
