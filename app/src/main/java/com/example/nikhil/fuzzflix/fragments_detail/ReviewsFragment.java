package com.example.nikhil.fuzzflix.fragments_detail;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.example.nikhil.fuzzflix.R;
import com.example.nikhil.fuzzflix.data.ReviewData;
import com.example.nikhil.fuzzflix.loader.ReviewAsyncLoader;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by nikhil on 11/03/18.
 */

public class ReviewsFragment extends Fragment implements LoaderManager.LoaderCallbacks<List<ReviewData>>{

    int mMovieId;

    View mView;

    private RecyclerView mRecyclerView;

    private ReviewRecyclerView mReviewAdapter;

    private static final int ID_DETAIL_LOADER = 46;

    private static final String TAG = ReviewsFragment.class.getSimpleName();

    private ProgressBar mProgressBar;

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

        mRecyclerView = (RecyclerView) mView.findViewById(R.id.review_recycler_view);

        mReviewAdapter = new ReviewRecyclerView();

        mProgressBar = mView.findViewById(R.id.review_fragment_progress_bar);

        final GridLayoutManager layoutManager
                = new GridLayoutManager(this.getActivity(), 1);

        mRecyclerView.setLayoutManager(layoutManager);

        mRecyclerView.setHasFixedSize(true);

        mRecyclerView.setAdapter(mReviewAdapter);

        getLoaderManager().initLoader(ID_DETAIL_LOADER,null,this);
        return mView;
    }

    @Override
    public void onResume() {
        mProgressBar = mView.findViewById(R.id.review_fragment_progress_bar);
        super.onResume();
    }

    @Override
    public Loader<List<ReviewData>> onCreateLoader(int id, Bundle args) {
        ReviewAsyncLoader reviewAsyncLoader = new ReviewAsyncLoader(getContext(), mMovieId);
        reviewAsyncLoader.forceLoad();
        return reviewAsyncLoader;
    }




    @Override
    public void onLoadFinished(Loader<List<ReviewData>> loader, List<ReviewData> data) {

        mProgressBar.setVisibility(View.INVISIBLE);

        mReviewAdapter.swapList((ArrayList<ReviewData>) data);

//        TextView reviewTextView = getView().findViewById(R.id.tv_review_frag_1);
//
//        if (data != null && data.size() != 0){
//            reviewTextView.setText(data.get(0).getReviewContent());
//        } else {
//            reviewTextView.setText(getResources().getString(R.string.no_review));
//        }
    }

    @Override
    public void onLoaderReset(Loader<List<ReviewData>> loader) {

    }
}
