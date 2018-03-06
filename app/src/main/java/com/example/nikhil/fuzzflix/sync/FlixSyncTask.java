package com.example.nikhil.fuzzflix.sync;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;

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


    synchronized public static void syncMovie(Context context) {
        URL networkUrl;
        ContentValues[] contentValuesArray;
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

                    // bulk insert the data to the table
                    movieContentResolver.bulkInsert(Contract.MainMoviesEntry.CONTENT_URI, contentValuesArray);

                }

            }catch (IOException e) {
                e.printStackTrace();
            }
        }
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
