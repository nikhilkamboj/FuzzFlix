package com.example.nikhil.fuzzflix.loader;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.AsyncTaskLoader;
import android.util.Log;

import com.example.nikhil.fuzzflix.data.ReviewData;
import com.example.nikhil.fuzzflix.utilities.JsonUtils;
import com.example.nikhil.fuzzflix.utilities.NetworkUtils;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by nikhil on 12/03/18.
 */

public class ReviewAsyncLoader extends AsyncTaskLoader<List<ReviewData>> {

    private static final String TAG = ReviewAsyncLoader.class.getSimpleName();
    int mMovieId;

    public ReviewAsyncLoader(@NonNull Context context, int movieId) {
        super(context);
        mMovieId =movieId;
    }

    @Nullable
    @Override
    public List<ReviewData> loadInBackground() {
        URL url = new NetworkUtils().buildReviewUrl(mMovieId); // dummy value
        Log.v(TAG, "URL:" +url);

        // handle in background thread
        String reviewString = null;
        try{
            reviewString = new NetworkUtils().getHttpUrlRequest(url);
        }catch (IOException e) {
            e.printStackTrace();
        }
        Log.v(TAG, "review :" + reviewString);

        ArrayList<ReviewData> reviewDataArrayList = new ArrayList<>();
        reviewDataArrayList = new JsonUtils().getReviewFromJsonString(reviewString);

        Log.v(TAG, "list: " +reviewDataArrayList);

        return reviewDataArrayList;
    }

    @Override
    public void deliverResult(@Nullable List<ReviewData> data) {
        super.deliverResult(data);
        if (data != null) {
            Log.v(TAG,"result delivered " +data.size());
        }

    }
}
