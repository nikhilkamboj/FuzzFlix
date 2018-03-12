package com.example.nikhil.fuzzflix.fragments_detail;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.nikhil.fuzzflix.R;

/**
 * Created by nikhil on 11/03/18.
 */

public class ReviewsFragment extends Fragment /*implements AsyncTaskLoader<List<String>>*/ {

    int mMovieId;

    View mView;

    private static final int ID_DETAIL_LOADER = 46;


    public ReviewsFragment() {
    }

    public ReviewsFragment(int mMovieId) {
        this.mMovieId = mMovieId;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.reviews_fragment, container, false);
        return mView;
    }
}
