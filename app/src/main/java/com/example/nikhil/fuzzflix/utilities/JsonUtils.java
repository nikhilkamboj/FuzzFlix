package com.example.nikhil.fuzzflix.utilities;

import android.content.Context;

import com.example.nikhil.fuzzflix.constants.AppConstants;
import com.example.nikhil.fuzzflix.data.DisplayData;

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
    public ArrayList<DisplayData> getMovieDataFromJsonString(Context context, String jsonObjectString){

        DisplayData dataObject;

        ArrayList<DisplayData> dataObjectsArrayList = new ArrayList<DisplayData>();

        try {
            JSONObject jsonObject = new JSONObject(jsonObjectString);
            JSONArray resultArray = jsonObject.getJSONArray(AppConstants.getResultArrayAttribute());

            if(resultArray.length() == 0){
                return null;
            }


            for(int i = 0; i < resultArray.length(); i++){
                dataObject = new DisplayData();
                JSONObject resultArrayObjects = resultArray.getJSONObject(i);

                dataObject.setTitleOfMovie(resultArrayObjects.getString(AppConstants.getTitleAttribute()));
                dataObject.setDateOfRelease(resultArrayObjects.getString(AppConstants.getReleaseDateAttribute()));
                dataObject.setPosterPath(resultArrayObjects.getString(AppConstants.getMainPosterPathAttribute()));
                dataObject.setBackGroundPosterPath(resultArrayObjects.getString(AppConstants.getBackgroundPosterPathAttribute()));
                dataObject.setRatingOfMovie(resultArrayObjects.getString(AppConstants.getRatingAttribute()));
                dataObject.setOverView(resultArrayObjects.getString(AppConstants.getOverviewAttribute()));
                dataObject.setMovieId(resultArrayObjects.getString(AppConstants.getMovieId()));
                dataObject.setMovieId(resultArrayObjects.getString(AppConstants.getMovieVoteCount()));

                dataObjectsArrayList.add(dataObject);
            }
        }catch (JSONException e){
            e.printStackTrace();
        }

        return dataObjectsArrayList;
    }


}
