package com.example.nikhil.fuzzflix;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.widget.ProgressBar;

import com.example.nikhil.fuzzflix.fragments.FavouriteFragment;
import com.example.nikhil.fuzzflix.fragments.PopularFragment;
import com.example.nikhil.fuzzflix.fragments.TopRatedFragment;
import com.example.nikhil.fuzzflix.fragments.ViewPagerAdapter;

public class MainActivity extends AppCompatActivity{




//    default page value for now
    public int pageIndexValue = 1;

    public MovieDataAdapter mMovieAdapter;

    public RecyclerView mRecyclerView;

    public ProgressBar mProgressBar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ViewPager viewPager = (ViewPager) findViewById(R.id.view_pager);
        ViewPagerAdapter viewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager());

        viewPagerAdapter.add(new PopularFragment(), "popular");
        viewPagerAdapter.add(new TopRatedFragment(), "top rated");
        viewPagerAdapter.add(new FavouriteFragment(), "favourites");

        viewPager.setAdapter(viewPagerAdapter);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);


    }

}
