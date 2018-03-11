package com.example.nikhil.fuzzflix.fragments_detail;

import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.ArrayList;

/**
 * Created by nikhil on 11/03/18.
 */

public class DetailViewPagerAdapter extends FragmentPagerAdapter {

    ArrayList<Fragment> fragmentArrayList = new ArrayList<>();
    ArrayList<String> headingList = new ArrayList<>();


    public DetailViewPagerAdapter(FragmentManager fm) {
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
