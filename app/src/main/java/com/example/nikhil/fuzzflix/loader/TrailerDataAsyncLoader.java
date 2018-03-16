package com.example.nikhil.fuzzflix.loader;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.AsyncTaskLoader;
import android.util.Log;

import com.example.nikhil.fuzzflix.data.TrailerData;
import com.example.nikhil.fuzzflix.utilities.JsonUtils;
import com.example.nikhil.fuzzflix.utilities.NetworkUtils;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by nikhil on 13/03/18.
 */

public class TrailerDataAsyncLoader extends AsyncTaskLoader<List<TrailerData>> {

    private static final String TAG = TrailerDataAsyncLoader.class.getSimpleName();
    int mMovieId;
    ArrayList<TrailerData> trailerDataArrayList;


    public TrailerDataAsyncLoader(@NonNull Context context, int movieId) {
        super(context);
        mMovieId = movieId;
    }

    @Nullable
    @Override
    public List<TrailerData> loadInBackground() {

        Log.i(TAG, "" + mMovieId);

        URL urlTrailerApiTMDB = new NetworkUtils().buildTrailerUrl(mMovieId);

        // setting http on back thread.
        try{
            String jsonString = new NetworkUtils().getHttpUrlRequest(urlTrailerApiTMDB);
            trailerDataArrayList = new JsonUtils().getTrailerFromJsonString(jsonString);
        }catch (IOException e) {
            e.printStackTrace();
        }

        return trailerDataArrayList;
    }
}
