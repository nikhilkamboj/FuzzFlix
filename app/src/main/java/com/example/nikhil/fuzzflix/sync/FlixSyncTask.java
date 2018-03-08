package com.example.nikhil.fuzzflix.sync;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.net.Uri;
import android.util.Log;

import com.example.nikhil.fuzzflix.constants.AppConstants;
import com.example.nikhil.fuzzflix.database.Contract;
import com.example.nikhil.fuzzflix.utilities.JsonUtils;
import com.example.nikhil.fuzzflix.utilities.NetworkUtils;

import java.io.IOException;
import java.net.URL;

/**
 * Created by nikhil on 06/03/18.
 *
 * calling api's and then filling up the db
 */

public class FlixSyncTask {

    private static final String TAG = FlixSyncTask.class.getSimpleName();


    synchronized public static void syncMovie(Context context) {
        URL networkUrl;
        ContentValues[] contentValuesArray;


        Log.v(TAG,"into sync deleting existng db");

        /**
         * first deleting the required db Data and then adding it again
         */
        Uri mainUri = Contract.MainMoviesEntry.CONTENT_URI;
        String selection = Contract.MainMoviesEntry.MOVIE_IS_FAVOURITE + "=?";
        String[] selectionArgs = {"0"};
        context.getContentResolver().delete(mainUri,selection,selectionArgs);

        Log.v(TAG,"db deleted");


        for (int i = 0; i <= 1; i++) {
            try {
                // getting the api url
                networkUrl = getFilteredUrl(i);

                // getting json data from the url
                String  jsonWeatherResponse = new NetworkUtils().getHttpUrlRequest(networkUrl);

                // converting json  to contentValues array.
                if (i == 0) {
                    contentValuesArray = new JsonUtils().getMovieDataFromJsonString(context,
                            jsonWeatherResponse, AppConstants.getPopularFilterFlag());
                } else {
                    contentValuesArray = new JsonUtils().getMovieDataFromJsonString(context,
                            jsonWeatherResponse, AppConstants.getTopRatedFlag());
                }

                if (contentValuesArray != null && contentValuesArray.length != 0) {
                    ContentResolver movieContentResolver = context.getContentResolver();

                    // first deletes all data except the one with isFavourite as 1 and then fill up all the data


                    Log.v(TAG,"bulk insert of part"+ i);
                    // bulk insert the data to the table
                    movieContentResolver.bulkInsert(Contract.MainMoviesEntry.CONTENT_URI, contentValuesArray);

                }

            }catch (IOException e) {
                e.printStackTrace();
            }
        }
        // check again out here about the cursor i.e again a query command
        Log.v(TAG,"bulk insertion done");
    }


    private static URL getFilteredUrl(int code) {
        URL networkUrl = null;
        switch (code) {
            case 0:
                networkUrl = new NetworkUtils().buildBaseUrl(AppConstants.getPopularFilterValue(), 1);
                break;
            case 1:
                networkUrl = new NetworkUtils().buildBaseUrl(AppConstants.getTopRatedFilterValue(),1);
                break;
        }
        return networkUrl;
    }
}
