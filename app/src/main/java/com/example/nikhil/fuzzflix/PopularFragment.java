package com.example.nikhil.fuzzflix;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.nikhil.fuzzflix.constants.AppConstants;
import com.example.nikhil.fuzzflix.data.DisplayData;
import com.example.nikhil.fuzzflix.utilities.JsonUtils;
import com.example.nikhil.fuzzflix.utilities.NetworkUtils;

import java.net.URL;
import java.util.ArrayList;

/**
 * Created by nikhil on 26/02/18.
 */

public class PopularFragment extends Fragment implements MovieDataAdapter.ListItemClickListener {

    private static final String TAG = PopularFragment.class.getSimpleName() ;

    View mRootView;

    RecyclerView mRecyclerView;

    MovieDataAdapter mMovieAdapter;

   // public ProgressBar mProgressBar;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        mRootView =  inflater.inflate(R.layout.popular_filter_fragment,container,false);

        mRecyclerView  = mRootView.findViewById(R.id.popular_recycler_view);

        mMovieAdapter = new MovieDataAdapter(this);

        //mProgressBar = (ProgressBar) mRootView.findViewById(R.id.popular_recycler_view);

        final GridLayoutManager layoutManager
                = new GridLayoutManager(this.getActivity(), 2);

        mRecyclerView.setLayoutManager(layoutManager);

        mRecyclerView.setHasFixedSize(true);

        mRecyclerView.setAdapter(mMovieAdapter);

        loadMovieData(AppConstants.getPopularFilterValue());

        return mRootView;
    }


    /**
     * this method calls AsyncTask to start execution by sending them selectedFilter which is filter for
     * popular or top_rated movie. this sends one of the two to the doInBackground() for further executions.
     *
     * @param selectedFilter type of data user wats to receive parameter (popular or top_rated)
     */
    private void loadMovieData(String selectedFilter){
        if(selectedFilter == AppConstants.getPopularFilterValue()){
           // mProgressBar.setVisibility(View.VISIBLE);
            new FetchMovieData().execute(AppConstants.getPopularFilterValue());
        }else{
            //mProgressBar.setVisibility(View.VISIBLE);
            new FetchMovieData().execute(AppConstants.getTopRatedFilterValue());
        }
    }


    /**
     * lets the system execute network calls in a another thread, therefore helps in achieving a better UI
     *
     * {@link FetchMovieData} is inner class extends {@link AsyncTask}
     *
     * overrides doInBackground and onPostExecute
     *
     * RecyclerView(ADAPTER) created with xml file for lists, some tweaks into JSON class done, refactored the code for cooments
     */
    public class FetchMovieData extends AsyncTask<String, Void, ArrayList<DisplayData>> {

        /**
         * this method helps to set a targeted URL and also a network connection to that URL using
         * NetworkUtils methods such as buildBaseUrl() and getHttpUrlRequest().
         *
         * @param params a String array that has values that helps creating the url for network call
         * @return a ArrayList<DisplayData> to the onPostExecute method that needs to be displayed on-screen
         */
        @Override
        protected ArrayList<DisplayData> doInBackground(String... params) {
            if(params.length == 0){
                return null;
            }

            String filterType = params[0];

            ArrayList<DisplayData> resultArrayList = null;

            NetworkUtils networkUtils = new NetworkUtils();

            URL url = networkUtils.buildBaseUrl(filterType,1);

            String movieData = null;

            try {

                Log.v(TAG, "loading data");

                movieData = networkUtils.getHttpUrlRequest(url);

                Log.v(TAG, "data loaded");

                JsonUtils jsonUtils = new JsonUtils();

                Log.v(TAG, "converting Json to DisplayObject arrayList");

                resultArrayList = jsonUtils.getMovieDataFromJsonString(getContext(), movieData);

                Log.v(TAG, "converted");

            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }

            Log.v(TAG, "returning result to onPost");
            return resultArrayList;
        }


        /**
         * this method merges the backward thread to the main/UI thread and therefore lets the data received to shown at
         * the UI.
         * here it calls the setMovieData() of the which pings MovieAdapter which starts creating viewHolder once it receives
         * data to be shown.
         *
         * @param resultArrayList this the arrayList of the DisplayData objects
         */
        @Override
        protected void onPostExecute(ArrayList<DisplayData> resultArrayList) {
            Log.v(TAG, "calling adapter's setMovieData");
           // mProgressBar.setVisibility(View.INVISIBLE);
            mMovieAdapter.setMovieData(resultArrayList);
        }
    }

    /**
     * implemented method for the interface {@link com.example.nikhil.fuzzflix.MovieDataAdapter.ListItemClickListener}
     *
     *this overridden method would get executed upon a click to viewHolder and redirects to the DetailPage Activity
     *
     * @param displayDataAtPosition  DisplayData object at that particular position
     */
    @Override
    public void onListItemClick(DisplayData displayDataAtPosition) {
        Intent intent = new Intent(getContext(), DetailPage.class);

        intent.putExtra(AppConstants.getTitleAttribute(),displayDataAtPosition.getTitleOfMovie());
        intent.putExtra(AppConstants.getReleaseDateAttribute(),displayDataAtPosition.getDateOfRelease());
        intent.putExtra(AppConstants.getBackgroundPosterPathAttribute(),displayDataAtPosition.getBackGroundPosterPath());
        intent.putExtra(AppConstants.getRatingAttribute(),displayDataAtPosition.getRatingOfMovie());
        intent.putExtra(AppConstants.getOverviewAttribute(),displayDataAtPosition.getOverView());

        startActivity(intent);
    }

}
