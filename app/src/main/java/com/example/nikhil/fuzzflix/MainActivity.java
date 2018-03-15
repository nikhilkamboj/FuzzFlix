package com.example.nikhil.fuzzflix;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

import com.example.nikhil.fuzzflix.fragments_detail.FragmentViewPager;
import com.example.nikhil.fuzzflix.fragments_main.FavouriteFragment;
import com.example.nikhil.fuzzflix.fragments_main.PopularFragment;
import com.example.nikhil.fuzzflix.fragments_main.TopRatedFragment;
import com.example.nikhil.fuzzflix.fragments_main.MainViewPagerAdapter;
import com.example.nikhil.fuzzflix.sync.FlixSyncUtils;

public class MainActivity extends AppCompatActivity{

    MainViewPagerAdapter viewPagerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FlixSyncUtils.initialize(this);

        ViewPager viewPager = (ViewPager) findViewById(R.id.view_pager);
        viewPagerAdapter = new MainViewPagerAdapter(getSupportFragmentManager());

        viewPagerAdapter.add(new PopularFragment(), "popular");
        viewPagerAdapter.add(new TopRatedFragment(), "top rated");
        viewPagerAdapter.add(new FavouriteFragment(), "favourites");

        viewPager.setAdapter(viewPagerAdapter);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);


    }

    private ViewPager.OnPageChangeListener onPageChangeListener = new ViewPager.OnPageChangeListener() {

        int currentPosition = 0;

        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

        }

        @Override
        public void onPageSelected(int newPosition) {
            FragmentViewPager newFragmentViewPager = (FragmentViewPager) viewPagerAdapter.getItem(newPosition);
            newFragmentViewPager.onResumeFragment();

            FragmentViewPager currentFragmentViewPager = (FragmentViewPager) viewPagerAdapter.getItem(currentPosition);
            currentFragmentViewPager.onPauseFragment();
        }

        @Override
        public void onPageScrollStateChanged(int state) {

        }
    };

}
