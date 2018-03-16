package com.example.nikhil.fuzzflix.fragments_main;

import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.nikhil.fuzzflix.DetailPage;
import com.example.nikhil.fuzzflix.MovieDataAdapter;
import com.example.nikhil.fuzzflix.R;
import com.example.nikhil.fuzzflix.constants.AppConstants;
import com.example.nikhil.fuzzflix.database.Contract;
import com.example.nikhil.fuzzflix.fragments_detail.FragmentViewPager;

/**
 * Created by nikhil on 26/02/18.
 */

public class FavouriteFragment extends Fragment implements MovieDataAdapter.ListItemClickListener
        , LoaderManager.LoaderCallbacks<Cursor>, FragmentViewPager {

    private static final String[] projection = {
            Contract.MainMoviesEntry.MOVIES_ID,
            Contract.MainMoviesEntry.MOVIE_TITLE,
            Contract.MainMoviesEntry.MOVIE_OVERVIEW,
            Contract.MainMoviesEntry.MOVIE_FRONT_POSTER_PATH,
            Contract.MainMoviesEntry.MOVIE_BACK_POSTER_PATH,
            Contract.MainMoviesEntry.MOVIE_VOTE_AVERAGE,
            Contract.MainMoviesEntry.MOVIE_VOTE_COUNT
    };

    private static final String TAG = FavouriteFragment.class.getSimpleName() ;


    /**
     * this for the type of loader, if there are different loading options
     */
    private static final int ID_FAVOURITE_LOADER = 41;

    View mRootView;

    RecyclerView mRecyclerView;

    MovieDataAdapter mMovieAdapter;

    public ProgressBar mProgressBar;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mRootView = inflater.inflate(R.layout.favourites_filter_fragment,container,false);

        mRecyclerView = (RecyclerView) mRootView.findViewById(R.id.favourite_recycler_view);

        mProgressBar = (ProgressBar) mRootView.findViewById(R.id.favourite_fragment_progress_bar);

        mMovieAdapter = new MovieDataAdapter(this);

        final GridLayoutManager layoutManager
                = new GridLayoutManager(this.getActivity(), 2);

        mRecyclerView.setLayoutManager(layoutManager);

        mRecyclerView.setHasFixedSize(true);

        mRecyclerView.setAdapter(mMovieAdapter);


//        //loadMovieData(AppConstants.getPopularFilterValue());
//        getLoaderManager().initLoader(ID_FAVOURITE_LOADER,null,this);

        return mRootView;
    }

    @Override
    public void onResume() {
        mProgressBar = (ProgressBar) mRootView.findViewById(R.id.favourite_fragment_progress_bar);
        Log.i(TAG, "fav fragment has been resumed");
        onResumeFragment();
        super.onResume();
    }

    @Override
    public void onPause() {
        Log.i(TAG, "fav fragment has been paused!!!");
        onPauseFragment();
        super.onPause();
    }

    @Override
    public void onListItemClick(int movie_id) {
        Intent intent = new Intent(getContext(), DetailPage.class);

        // sending the Mid would work as position might even change in due to data agregation of twp apis
        intent.putExtra(AppConstants.getKeyMovieId(),movie_id);

        startActivity(intent);
    }


    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        switch (id) {
            case ID_FAVOURITE_LOADER:
                Uri mPopularUri = Contract.MainMoviesEntry.CONTENT_URI;
                String selection = Contract.MainMoviesEntry.MOVIE_IS_FAVOURITE+ AppConstants.getSelectionEqualQuestionString();
                String[] selectionArgs = {AppConstants.getSelectionArgsFavourite()};

                return new CursorLoader(getContext(),
                        mPopularUri,
                        projection,
                        selection,
                        selectionArgs,
                        null);
        }
        return null;
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {

        TextView textView = (TextView) mRootView.findViewById(R.id.tv_not_added);

        mProgressBar.setVisibility(View.INVISIBLE);
        // cursor swapping taking place
        mMovieAdapter.swapCursor(data);
        Log.v(TAG,"cursor swapping taking place ");
        if (data.getCount() == 0 || data == null) {
            textView.setVisibility(View.VISIBLE);
        } else {
            textView.setVisibility(View.INVISIBLE);
        }
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {

    }

    /**
     * interface methods to handle change of handling of loading of data in ViewPager
     * handling loader destroying and resuming once we swap away and come back to the cursor
     */
    @Override
    public void onPauseFragment() {
        getLoaderManager().destroyLoader(ID_FAVOURITE_LOADER);
    }

    @Override
    public void onResumeFragment() {
        getLoaderManager().initLoader(ID_FAVOURITE_LOADER,null,this);

    }
}
