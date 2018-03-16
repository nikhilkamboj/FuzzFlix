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
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.example.nikhil.fuzzflix.DetailPage;
import com.example.nikhil.fuzzflix.MovieDataAdapter;
import com.example.nikhil.fuzzflix.R;
import com.example.nikhil.fuzzflix.constants.AppConstants;
import com.example.nikhil.fuzzflix.database.Contract;

/**
 * Created by nikhil on 26/02/18.
 */

public class TopRatedFragment extends Fragment implements MovieDataAdapter.ListItemClickListener, LoaderManager.LoaderCallbacks<Cursor> {



    private static final String[] projection = {
            Contract.MainMoviesEntry.MOVIES_ID,
            Contract.MainMoviesEntry.MOVIE_TITLE,
            Contract.MainMoviesEntry.MOVIE_OVERVIEW,
            Contract.MainMoviesEntry.MOVIE_FRONT_POSTER_PATH,
            Contract.MainMoviesEntry.MOVIE_BACK_POSTER_PATH,
            Contract.MainMoviesEntry.MOVIE_VOTE_AVERAGE,
            Contract.MainMoviesEntry.MOVIE_VOTE_COUNT
    };

    private static final int ID_TOP_RATED_LOADER = 45;

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

        mRootView =  inflater.inflate(R.layout.top_rated_filter_fragment,container,false);

        mRecyclerView  = mRootView.findViewById(R.id.top_rated_recycler_view);

        mMovieAdapter = new MovieDataAdapter(this);

        mProgressBar = (ProgressBar) mRootView.findViewById(R.id.top_rated_fragment_progress_bar);

        final GridLayoutManager layoutManager
                = new GridLayoutManager(this.getActivity(), 2);

        mRecyclerView.setLayoutManager(layoutManager);
//
        mRecyclerView.setHasFixedSize(true);

        mRecyclerView.setAdapter(mMovieAdapter);

        //loadMovieData(AppConstants.getTopRatedFilterValue());
        getLoaderManager().initLoader(ID_TOP_RATED_LOADER,null,this);

        return mRootView;
    }

    @Override
    public void onListItemClick(int movie_id) {
        Intent intent = new Intent(getContext(), DetailPage.class);

        // sending the Mid would work as position might even change in due to data agregation of twp apis
        intent.putExtra(AppConstants.getKeyMovieId(), movie_id);

        startActivity(intent);
    }


    // creating the loader


    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        switch (id) {
            case ID_TOP_RATED_LOADER:
                Uri mUriTopRated = Contract.MainMoviesEntry.CONTENT_URI;
                String selection = Contract.MainMoviesEntry.MOVIE_IS_TOP_RATED + AppConstants.getSelectionEqualQuestionString();
                String[] selectionArgs = {AppConstants.getSelectionArgsTopRated()};

                return new CursorLoader(getContext(),
                        mUriTopRated,
                        projection,
                        selection,
                        selectionArgs,
                        null);
        }
        return null;
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        mProgressBar.setVisibility(View.INVISIBLE);
        mMovieAdapter.swapCursor(data);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {

    }
}
