package com.example.nikhil.fuzzflix.utilities;

import android.content.ContentValues;
import android.content.Context;
import android.util.Log;

import com.example.nikhil.fuzzflix.constants.AppConstants;
import com.example.nikhil.fuzzflix.data.DisplayData;
import com.example.nikhil.fuzzflix.data.ReviewData;
import com.example.nikhil.fuzzflix.data.TrailerData;
import com.example.nikhil.fuzzflix.database.Contract;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by nikhil on 11/01/18.
 */

/**
 * this class is used to traverse the JSON object received to extract useful data from it through getMovieDataFromJsonString()
 * which returns for now a simple String array
 */

public class JsonUtils {

    private static final String TAG = JsonUtils.class.getSimpleName();

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

    public ArrayList<ReviewData> getReviewFromJsonString(String jsonString) {

        ArrayList<ReviewData> reviewList = new ArrayList<>();


        try {
            JSONObject jsonObject = new JSONObject(jsonString);
            JSONArray  jsonArray  = jsonObject.getJSONArray(AppConstants.getResultArrayAttribute());
            ReviewData reviewData;

            if(jsonArray.length() == 0){
                return null;
            }

            // we have resultArray, there will be a JsonObject at each index, so get a json object
            // first then attribute values from that object.

            for (int i = 0; i < jsonArray.length(); i++) {
                reviewData = new ReviewData();
                JSONObject indexJsonObject = jsonArray.getJSONObject(i);

                reviewData.setId(indexJsonObject.getString(AppConstants.getReviewId()));
                reviewData.setAuthorName(indexJsonObject.getString(AppConstants.getAuthorName()));
                reviewData.setReviewContent(indexJsonObject.getString(AppConstants.getReviewContent()));

                reviewList.add(reviewData);
            }


        }catch (JSONException e) {
            e.printStackTrace();
        }


        return reviewList;
    }


    public ArrayList<TrailerData> getTrailerFromJsonString(String jsonString) {
        ArrayList<TrailerData> trailerDataArrayList = new ArrayList<>();

        try{
            JSONObject jsonObject = new JSONObject(jsonString);
            JSONArray  jsonArray  = jsonObject.getJSONArray(AppConstants.getResultArrayAttribute());
            Log.i(TAG, "" +jsonArray);
            TrailerData trailerData;

            if (jsonArray.length() == 0) {
                return null;
            }

            for (int i = 0; i < jsonArray.length(); i++) {
                trailerData = new TrailerData();
                JSONObject indexJsonObject = jsonArray.getJSONObject(i);

                trailerData.setId(indexJsonObject.getString(AppConstants.getTrailerId()));
                trailerData.setKey(indexJsonObject.getString(AppConstants.getTrailerKey()));
                trailerData.setTrailer(indexJsonObject.getString(AppConstants.getTrailerName()));

                trailerDataArrayList.add(trailerData);
            }

        }catch (JSONException e) {
            e.printStackTrace();
        }

        return trailerDataArrayList;
    }




}
