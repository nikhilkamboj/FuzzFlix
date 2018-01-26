package com.example.nikhil.fuzzflix;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;

import com.example.nikhil.fuzzflix.constants.AppConstants;
import com.example.nikhil.fuzzflix.data.DisplayData;
import com.example.nikhil.fuzzflix.utilities.JsonUtils;
import com.example.nikhil.fuzzflix.utilities.NetworkUtils;

import java.net.URL;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements MovieDataAdapter.ListItemClickListener{




//    default page value for now
    public int pageIndexValue = 1;

    public MovieDataAdapter mMovieAdapter;

    public RecyclerView mRecyclerView;

    public ProgressBar mProgressBar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mRecyclerView = findViewById(R.id.recycle_view_container);

        mProgressBar = findViewById(R.id.main_activity_progress_bar);

        final GridLayoutManager layoutManager
                = new GridLayoutManager(this, 2);

        mRecyclerView.setLayoutManager(layoutManager);


//        mRecyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.HORIZONTAL));

//        mRecyclerView.setItemAnimator(new DefaultItemAnimator());


        mRecyclerView.setHasFixedSize(true);

        mMovieAdapter = new MovieDataAdapter(this);

        mRecyclerView.setAdapter(mMovieAdapter);

        // default loading of popular movie data
        loadMovieData(AppConstants.getPopularFilterValue());


    }


    /**
     * this method calls AsyncTask to start execution by sending them selectedFilter which is filter for
     * popular or top_rated movie. this sends one of the two to the doInBackground() for further executions.
     *
     * @param selectedFilter type of data user wats to receive parameter (popular or top_rated)
     */
    private void loadMovieData(String selectedFilter){
        if(selectedFilter == AppConstants.getPopularFilterValue()){
            mProgressBar.setVisibility(View.VISIBLE);
           new FetchMovieData().execute(AppConstants.getPopularFilterValue());
        }else{
            mProgressBar.setVisibility(View.VISIBLE);
            new FetchMovieData().execute(AppConstants.getTopRatedFilterValue());
        }
    }


    /**
     * overriding onCreateOptionsMenu for the purpose of inflating the menu resource file and letting it visible in Main Activity
     * @param menu
     * @return returns boolean if menu inflated or not
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // getting MenuInflater instance
        MenuInflater menuInflater = getMenuInflater();

        // inflating the menu layout
        menuInflater.inflate(R.menu.filter,menu);

        return true;
    }

    /**
     * overriding the onOptionsItemSelected(), if any of the menu item is clicked this
     * callback is called
     * handling all menu click events over here.
     * for now has two tasks 1. if popular is clicked  2. if top_rated is clicked
     *
     * further calls loadMovieData that further executes the AsyncTask as per the input.
     *
     * @param clickedItem the clicked menu item
     * @return boolean
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem clickedItem) {
        // getting id of the clicked item
        int id = clickedItem.getItemId();
        if(id == R.id.top_rated_menu_item){
            clickedItem.setChecked(true);
            loadMovieData(AppConstants.getTopRatedFilterValue());
        }else{
            clickedItem.setChecked(true);
            loadMovieData(AppConstants.getPopularFilterValue());
        }

        return super.onOptionsItemSelected(clickedItem);
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
    public class FetchMovieData extends AsyncTask<String, Void, ArrayList<DisplayData>>{

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

            URL url = networkUtils.buildBaseUrl(filterType,pageIndexValue);

            String movieData = null;

            try {

                movieData = networkUtils.getHttpUrlRequest(url);

                JsonUtils jsonUtils = new JsonUtils();

                resultArrayList = jsonUtils.getMovieDataFromJsonString(MainActivity.this, movieData);

            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }

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
            mProgressBar.setVisibility(View.INVISIBLE);
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
        Intent intent = new Intent(this, DetailPage.class);

        intent.putExtra(AppConstants.getTitleAttribute(),displayDataAtPosition.getTitleOfMovie());
        intent.putExtra(AppConstants.getReleaseDateAttribute(),displayDataAtPosition.getDateOfRelease());
        intent.putExtra(AppConstants.getBackgroundPosterPathAttribute(),displayDataAtPosition.getBackGroundPosterPath());
        intent.putExtra(AppConstants.getRatingAttribute(),displayDataAtPosition.getRatingOfMovie());
        intent.putExtra(AppConstants.getOverviewAttribute(),displayDataAtPosition.getOverView());

        startActivity(intent);
    }


    private void setmProgressBarVisible(){
        mProgressBar.setVisibility(View.VISIBLE);
    }

    private void setmProgressBarInVisible(){
        mProgressBar.setVisibility(View.INVISIBLE);
    }
}
