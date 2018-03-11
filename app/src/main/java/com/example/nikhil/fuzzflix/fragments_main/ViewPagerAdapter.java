package com.example.nikhil.fuzzflix.fragments_main;

import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.ArrayList;

/**
 * Created by nikhil on 26/02/18.
 */

public class ViewPagerAdapter extends FragmentPagerAdapter {

    ArrayList<Fragment> fragmentArrayList = new ArrayList<>();
    ArrayList<String>   headingList = new ArrayList<>();

    public ViewPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        return fragmentArrayList.get(position);
    }

    @Override
    public int getCount() {
        return fragmentArrayList.size();
    }

    public void add(Fragment fragment, String heading) {
        fragmentArrayList.add(fragment);
        headingList.add(heading);
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return headingList.get(position);
    }
}
