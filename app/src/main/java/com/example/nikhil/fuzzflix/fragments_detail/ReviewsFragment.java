package com.example.nikhil.fuzzflix.fragments_detail;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.nikhil.fuzzflix.R;
import com.example.nikhil.fuzzflix.data.ReviewData;
import com.example.nikhil.fuzzflix.loader.ReviewAsyncLoader;

import java.util.List;

/**
 * Created by nikhil on 11/03/18.
 */

public class ReviewsFragment extends Fragment implements LoaderManager.LoaderCallbacks<List<ReviewData>>{

    int mMovieId;

    View mView;

    private static final int ID_DETAIL_LOADER = 46;

    private static final String TAG = ReviewsFragment.class.getSimpleName();

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
        // create a local list object and handle setting of text here only
        getLoaderManager().initLoader(ID_DETAIL_LOADER,null,this);
        return mView;
    }

    @Override
    public Loader<List<ReviewData>> onCreateLoader(int id, Bundle args) {
        ReviewAsyncLoader reviewAsyncLoader = new ReviewAsyncLoader(getContext(), mMovieId);
        reviewAsyncLoader.forceLoad();
        return reviewAsyncLoader;
    }




    @Override
    public void onLoadFinished(Loader<List<ReviewData>> loader, List<ReviewData> data) {

        TextView reviewTextView = getView().findViewById(R.id.tv_review_frag_1);

        if (data != null ){
            reviewTextView.setText(data.get(0).getReviewContent());
        } else {
            reviewTextView.setText("no review yet!!");
        }
    }

    @Override
    public void onLoaderReset(Loader<List<ReviewData>> loader) {

    }
}
