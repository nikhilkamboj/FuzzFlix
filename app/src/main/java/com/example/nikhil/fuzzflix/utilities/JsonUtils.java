package com.example.nikhil.fuzzflix.utilities;

import android.content.ContentValues;
import android.content.Context;

import com.example.nikhil.fuzzflix.constants.AppConstants;
import com.example.nikhil.fuzzflix.data.DisplayData;
import com.example.nikhil.fuzzflix.database.Contract;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by nikhil on 11/01/18.
 */

/**
 * this class is used to traverse the JSON object received to extract useful data from it through getMovieDataFromJsonString()
 * which returns for now a simple String array
 */

public class JsonUtils {

    /**
     * traverses the json Object and gets the required data to be shown on_screen.
     *  {
     *    "page":1,
     *    "total_results":19742,
     *    "total_pages":988,
     *    "results": [ {},{},{},{}, ........]
     *    .
     *    .
     *    .
     *    .
     *  }
     *
     *
     * @param context             which view called it
     * @param jsonObjectString    the JSON Object we received from HttpRequest call
     *
     *
     *@return ArrayList<DisplayDat> returns an arrayList of objects that contains required info for displaying on-screen.
     */
    public ContentValues[] getMovieDataFromJsonString(Context context, String jsonObjectString, String filterFlag){

        DisplayData dataObject;

        ContentValues[] contentValuesArrayList = null;

        try {
            JSONObject jsonObject = new JSONObject(jsonObjectString);
            JSONArray resultArray = jsonObject.getJSONArray(AppConstants.getResultArrayAttribute());
            contentValuesArrayList = new ContentValues[resultArray.length()];

            if(resultArray.length() == 0){
                return null;
            }


            for(int i = 0; i < resultArray.length(); i++){
                ContentValues cv = new ContentValues();
                dataObject = new DisplayData();
                JSONObject resultArrayObjects = resultArray.getJSONObject(i);

                cv.put(Contract.MainMoviesEntry.MOVIES_ID, resultArrayObjects.getString(AppConstants.getMovieId()));
                cv.put(Contract.MainMoviesEntry.MOVIE_TITLE,resultArrayObjects.getString(AppConstants.getTitleAttribute()));
                cv.put(Contract.MainMoviesEntry.MOVIE_OVERVIEW,resultArrayObjects.getString(AppConstants.getOverviewAttribute()));
                cv.put(Contract.MainMoviesEntry.MOVIE_RELEASE_DATE,resultArrayObjects.getString(AppConstants.getReleaseDateAttribute()));
                cv.put(Contract.MainMoviesEntry.MOVIE_VOTE_AVERAGE,resultArrayObjects.getString(AppConstants.getRatingAttribute()));
                cv.put(Contract.MainMoviesEntry.MOVIE_VOTE_COUNT,resultArrayObjects.getString(AppConstants.getMovieVoteCount()));
                cv.put(Contract.MainMoviesEntry.MOVIE_FRONT_POSTER_PATH,resultArrayObjects.getString(AppConstants.getMainPosterPathAttribute()));
                cv.put(Contract.MainMoviesEntry.MOVIE_BACK_POSTER_PATH,resultArrayObjects.getString(AppConstants.getBackgroundPosterPathAttribute()));

                // handling data fillup for both api's
                if (filterFlag == AppConstants.getPopularFilterFlag()) {
                    cv.put(Contract.MainMoviesEntry.MOVIE_IS_POPULAR,"1");
                } else if (filterFlag == AppConstants.getTopRatedFlag()) {
                    cv.put(Contract.MainMoviesEntry.MOVIE_IS_TOP_RATED,"1");
                }

                contentValuesArrayList[i] = cv;
            }
        }catch (JSONException e){
            e.printStackTrace();
        }

        return contentValuesArrayList;
    }


}
