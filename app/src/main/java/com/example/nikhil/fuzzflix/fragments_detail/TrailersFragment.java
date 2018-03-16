package com.example.nikhil.fuzzflix.fragments_detail;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.nikhil.fuzzflix.R;
import com.example.nikhil.fuzzflix.data.TrailerData;
import com.example.nikhil.fuzzflix.loader.TrailerDataAsyncLoader;
import com.example.nikhil.fuzzflix.utilities.NetworkUtils;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by nikhil on 11/03/18.
 */

public class TrailersFragment extends Fragment implements LoaderManager.LoaderCallbacks<List<TrailerData>>{

    View mView;
    int mMovieId;
    ArrayList<TrailerData> trailerDataArrayList;

    private static final int ID_DETAIL_LOADER = 49;


    private static final String TAG = TrailersFragment.class.getSimpleName();

    public TrailersFragment() {
    }

    public TrailersFragment(int mMovieId) {
        this.mMovieId = mMovieId;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.trailer_fragment,container,false);
        Log.i(TAG, "" + mMovieId);
        getLoaderManager().initLoader(ID_DETAIL_LOADER,null,this);
        return mView;
    }

    @Override
    public Loader<List<TrailerData>> onCreateLoader(int id, Bundle args) {
        TrailerDataAsyncLoader dataAsyncLoader = new TrailerDataAsyncLoader(getContext(), mMovieId);
        dataAsyncLoader.forceLoad();
        return dataAsyncLoader;
    }

    @Override
    public void onLoadFinished(Loader<List<TrailerData>> loader, List<TrailerData> data) {
        TrailerData trailerData = new TrailerData();
        Button button = (Button) getView().findViewById(R.id.button_trailer);

        if (data != null ){
            trailerData = data.get(0);
        } else {
            button.setText(getResources().getString(R.string.no_internet_conn));
        }
        // handling on click listener here.
        // sending this object to network method which returns URL.
        // then creating the intent.
        URL url = new NetworkUtils().buildYoutubeUrl(trailerData.getKey());
        Log.v(TAG, "url for video" + url);

        final Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setData(Uri.parse(String.valueOf(url)));

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(intent);
            }
        });

    }

    @Override
    public void onLoaderReset(Loader<List<TrailerData>> loader) {

    }
}
