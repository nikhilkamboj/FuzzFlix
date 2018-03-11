package com.example.nikhil.fuzzflix;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

import com.example.nikhil.fuzzflix.fragments_main.FavouriteFragment;
import com.example.nikhil.fuzzflix.fragments_main.PopularFragment;
import com.example.nikhil.fuzzflix.fragments_main.TopRatedFragment;
import com.example.nikhil.fuzzflix.fragments_main.MainViewPagerAdapter;
import com.example.nikhil.fuzzflix.sync.FlixSyncUtils;

public class MainActivity extends AppCompatActivity{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FlixSyncUtils.initialize(this);

        ViewPager viewPager = (ViewPager) findViewById(R.id.view_pager);
        MainViewPagerAdapter viewPagerAdapter = new MainViewPagerAdapter(getSupportFragmentManager());

        viewPagerAdapter.add(new PopularFragment(), "popular");
        viewPagerAdapter.add(new TopRatedFragment(), "top rated");
        viewPagerAdapter.add(new FavouriteFragment(), "favourites");

        viewPager.setAdapter(viewPagerAdapter);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);

    }

}
