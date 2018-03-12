package com.example.nikhil.fuzzflix.fragments_detail;

import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.nikhil.fuzzflix.R;
import com.example.nikhil.fuzzflix.database.Contract;

/**
 * Created by nikhil on 11/03/18.
 */

public class OverviewFragment extends Fragment implements LoaderManager.LoaderCallbacks<Cursor>{

    int mMovieId;
    View mView;


    private static final String TAG = OverviewFragment.class.getSimpleName();


    private static final String[] projection = {
            Contract.MainMoviesEntry.MOVIE_OVERVIEW
    };

    /**
     * this for the type of loader, if there are different loading options
     */
    private static final int ID_POPULAR_LOADER = 47;

    public OverviewFragment() {

    }

    public OverviewFragment(int mMovieId) {
        this.mMovieId = mMovieId;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mView =  inflater.inflate(R.layout.overview_fragment,container,false);

        Log.v(TAG, "movieId" + mMovieId);

        getLoaderManager().initLoader(ID_POPULAR_LOADER,null,this);

        return mView;
    }


    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        switch (id) {
            case ID_POPULAR_LOADER:
                Uri mPopularUri = Contract.MainMoviesEntry.CONTENT_URI;
                String selection = Contract.MainMoviesEntry.MOVIES_ID + "=?";
                String[] selectionArgs = {String.valueOf(mMovieId)};

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

        boolean cursorHasValidData = false;

        Cursor cursor = data;

        int count = data.getCount();

        if (data != null && data.moveToFirst()) {
            /* We have valid data, continue on to bind the data to the UI */
            cursorHasValidData = true;
        }

        TextView textView = (TextView) mView.findViewById(R.id.tv_overview_detail_page);
        textView.setText(data.getString(data.getColumnIndex(Contract.MainMoviesEntry.MOVIE_OVERVIEW)));
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {

    }
}
