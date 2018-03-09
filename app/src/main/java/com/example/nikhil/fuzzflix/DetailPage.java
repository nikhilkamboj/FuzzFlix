package com.example.nikhil.fuzzflix;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.nikhil.fuzzflix.constants.AppConstants;
import com.example.nikhil.fuzzflix.database.Contract;
import com.example.nikhil.fuzzflix.utilities.NetworkUtils;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.net.URL;
import java.net.URLDecoder;

public class DetailPage extends AppCompatActivity implements
        LoaderManager.LoaderCallbacks<Cursor> {



    private static final String[] projection = {
            Contract.MainMoviesEntry.MOVIES_ID,
            Contract.MainMoviesEntry.MOVIE_TITLE,
            Contract.MainMoviesEntry.MOVIE_OVERVIEW,
            Contract.MainMoviesEntry.MOVIE_FRONT_POSTER_PATH,
            Contract.MainMoviesEntry.MOVIE_BACK_POSTER_PATH,
            Contract.MainMoviesEntry.MOVIE_VOTE_AVERAGE,
            Contract.MainMoviesEntry.MOVIE_VOTE_COUNT
    };


    private static final String TAG = DetailPage.class.getSimpleName();

    private Uri mDetailUri;

    private int mMovieId;

    private static final int ID_DETAIL_LOADER = 46;

    ImageView mBackgroundPosterImageView;
    TextView  mTitleTextView;
    TextView  mRatingsTextView;
    TextView mOverViewTextView;
    TextView  mReleaseDateTextView;
    ProgressBar mImageViewProgressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_page);


        Intent intent = getIntent();

        mMovieId = intent.getIntExtra(AppConstants.getKeyMovieId(), 0);

        Log.v(TAG, "movie id:" + mMovieId);

        getSupportLoaderManager().initLoader(ID_DETAIL_LOADER,null,this);

//        String backgroundImagePath = intent.getStringExtra(AppConstants.getBackgroundPosterPathAttribute());
//
//        String titleOfMovie = intent.getStringExtra(AppConstants.getTitleAttribute());
//
//        String releaseDate = intent.getStringExtra(AppConstants.getReleaseDateAttribute());
//
//        String ratingOfMovie = intent.getStringExtra(AppConstants.getRatingAttribute());
//
//        String movieOverView = intent.getStringExtra(AppConstants.getOverviewAttribute());
//
//        boolean isBackgroundPoster = true;
//
//        URL imageUrl = new NetworkUtils().buildImageUrl(backgroundImagePath,isBackgroundPoster);
//
//        String backgroundImageUrl = null;
//
//        try{
//            backgroundImagePath = URLDecoder.decode(imageUrl.toString(),"UTF-8");
//        }catch (Exception e){
//            e.printStackTrace();
//        }

        mTitleTextView = (TextView) findViewById(R.id.tv_title);
        mRatingsTextView = (TextView) findViewById(R.id.tv_ratings);
        mReleaseDateTextView = (TextView) findViewById(R.id.tv_date_of_release);
        mOverViewTextView = (TextView) findViewById(R.id.tv_overview);
        mImageViewProgressBar = (ProgressBar) findViewById(R.id.iv_detail_page_progress_bar);
        mBackgroundPosterImageView = (ImageView) findViewById(R.id.detail_page_image_view);

//        Uri imageUri = Uri.parse(backgroundImagePath);
//        Context context = getBaseContext();
//        mBackgroundPosterImageView = (ImageView) findViewById(R.id.detail_page_image_view);
//        mImageViewProgressBar = (ProgressBar) findViewById(R.id.iv_detail_page_progress_bar);
//
//        mImageViewProgressBar.setVisibility(View.VISIBLE);
//        Picasso.with(context).load(imageUri)
//                .into(mBackgroundPosterImageView, new Callback() {
//                    @Override
//                    public void onSuccess() {
//                        mImageViewProgressBar.setVisibility(View.INVISIBLE);
//                    }
//
//                    @Override
//                    public void onError() {
//
//                    }
//                });
//
//        mTitleTextView.setText(titleOfMovie);
//        mReleaseDateTextView.setText(releaseDate);
//        mRatingsTextView.setText(ratingOfMovie + getResources().getString(R.string.base_rating_value));
//        mOverViewTextView.setText(movieOverView);

    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        switch (id) {
            case ID_DETAIL_LOADER:
                Uri mMainUri = Contract.MainMoviesEntry.CONTENT_URI;
                String selection = Contract.MainMoviesEntry.MOVIES_ID + "=?";
                String[] selectionArgs = {String.valueOf(mMovieId)};
                return new CursorLoader(getApplicationContext(),
                        mMainUri,
                        projection,
                        selection,
                        selectionArgs,
                        null);
        }
        return null;
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        //mRatingsTextView.setText(data.getInt(data.getColumnIndex(Contract.MainMoviesEntry.MOVIE_VOTE_AVERAGE)));
        //mTitleTextView.setText(data.getString(data.getColumnIndex(Contract.MainMoviesEntry.MOVIE_TITLE)));
        boolean cursorHasValidData = false;
        if (data != null && data.moveToFirst()) {
            /* We have valid data, continue on to bind the data to the UI */
            cursorHasValidData = true;
        }

        if (!cursorHasValidData) {
            /* No data to display, simply return and do nothing */
            return;
        }
        //mRatingsTextView.setText(data.getInt(data.getColumnIndex(Contract.MainMoviesEntry.MOVIE_VOTE_AVERAGE)));
        mTitleTextView.setText(data.getString(data.getColumnIndex(Contract.MainMoviesEntry.MOVIE_TITLE)));
//        int releaseDate = data.getInt(data.getColumnIndex(Contract.MainMoviesEntry.MOVIE_RELEASE_DATE));
//        mReleaseDateTextView.setText(data.getString(data.getColumnIndex(Contract.MainMoviesEntry.MOVIE_RELEASE_DATE)));
        mOverViewTextView.setText(data.getString(data.getColumnIndex(Contract.MainMoviesEntry.MOVIE_OVERVIEW)));
//
        String backgroundImagePath = data.getColumnName(data.getColumnIndex(Contract.MainMoviesEntry.MOVIE_BACK_POSTER_PATH));

        boolean isBackgroundPoster = true;

        URL imageUrl = new NetworkUtils().buildImageUrl(backgroundImagePath,isBackgroundPoster);

        String backgroundImageUrl = null;

        try{
            backgroundImageUrl = URLDecoder.decode(imageUrl.toString(),"UTF-8");
        }catch (Exception e){
            e.printStackTrace();
        }

        Uri imageUri = Uri.parse(backgroundImageUrl);
        Context context = getBaseContext();

        mImageViewProgressBar.setVisibility(View.VISIBLE);
        Picasso.with(context).load(imageUri)
                .into(mBackgroundPosterImageView, new Callback() {
                            @Override
                            public void onSuccess() {
                                mImageViewProgressBar.setVisibility(View.INVISIBLE);
                            }

                            @Override
                            public void onError() {

                            }
                        });

        Log.v(TAG, "data count:" + data.getCount());
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {

    }
}