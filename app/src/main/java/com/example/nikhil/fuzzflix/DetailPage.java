package com.example.nikhil.fuzzflix;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.nikhil.fuzzflix.constants.AppConstants;
import com.example.nikhil.fuzzflix.database.Contract;
import com.example.nikhil.fuzzflix.fragments_detail.DetailViewPagerAdapter;
import com.example.nikhil.fuzzflix.fragments_detail.OverviewFragment;
import com.example.nikhil.fuzzflix.fragments_detail.ReviewsFragment;
import com.example.nikhil.fuzzflix.fragments_detail.TrailersFragment;
import com.example.nikhil.fuzzflix.utilities.NetworkUtils;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.net.URL;
import java.net.URLDecoder;

public class DetailPage extends AppCompatActivity implements
        LoaderManager.LoaderCallbacks<Cursor> {

    private boolean movieAddedToFav;

    private ImageView imageView;

    private static final String[] projection = {
            Contract.MainMoviesEntry.MOVIES_ID,
            Contract.MainMoviesEntry.MOVIE_TITLE,
            Contract.MainMoviesEntry.MOVIE_OVERVIEW,
            Contract.MainMoviesEntry.MOVIE_RELEASE_DATE,
            Contract.MainMoviesEntry.MOVIE_FRONT_POSTER_PATH,
            Contract.MainMoviesEntry.MOVIE_BACK_POSTER_PATH,
            Contract.MainMoviesEntry.MOVIE_VOTE_AVERAGE,
            Contract.MainMoviesEntry.MOVIE_VOTE_COUNT,
            Contract.MainMoviesEntry.MOVIE_IS_FAVOURITE
    };


    private static final String TAG = DetailPage.class.getSimpleName();

    private Uri mDetailUri;

    private int mMovieId;

    private static final int ID_DETAIL_LOADER = 46;

    private static final int ID_UPDATE_LOADER = 50;

    ImageView mBackgroundPosterImageView;
    TextView  mTitleTextView;
    TextView  mRatingsTextView;
    TextView  mReleaseDateTextView;
    ProgressBar mImageViewProgressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        setContentView(R.layout.activity_detail_page);

        Intent intent = getIntent();

        mMovieId = intent.getIntExtra(AppConstants.getKeyMovieId(), 0);
        
        ViewPager viewPager = (ViewPager) findViewById(R.id.view_pager_detail_page);

        DetailViewPagerAdapter detailViewPagerAdapter = new DetailViewPagerAdapter(getSupportFragmentManager());

        detailViewPagerAdapter.add(new OverviewFragment(mMovieId), getResources().getString(R.string.detail_page_overview_fragment));
        detailViewPagerAdapter.add(new ReviewsFragment(mMovieId), getResources().getString(R.string.detail_page_reviews_fragment));
        detailViewPagerAdapter.add(new TrailersFragment(mMovieId), getResources().getString(R.string.detail_page_trailer_fragment));

        viewPager.setAdapter(detailViewPagerAdapter);

        final TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs_detail_page);
        tabLayout.setupWithViewPager(viewPager);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayOptions(actionBar.getDisplayOptions()
                | ActionBar.DISPLAY_SHOW_CUSTOM);
         imageView = new ImageView(actionBar.getThemedContext());
         imageView.setScaleType(ImageView.ScaleType.CENTER);

        ActionBar.LayoutParams layoutParams = new ActionBar.LayoutParams(
                ActionBar.LayoutParams.WRAP_CONTENT,
                ActionBar.LayoutParams.WRAP_CONTENT, Gravity.RIGHT
                | Gravity.CENTER_VERTICAL);
        layoutParams.rightMargin = 40;
        imageView.setLayoutParams(layoutParams);
        actionBar.setCustomView(imageView);

        getSupportLoaderManager().initLoader(ID_DETAIL_LOADER,null,this);

        View view = getLayoutInflater().inflate(R.layout.overview_fragment,null);


        mTitleTextView = (TextView) findViewById(R.id.tv_title);
        mRatingsTextView = (TextView) findViewById(R.id.tv_ratings);
        mReleaseDateTextView = (TextView) findViewById(R.id.tv_date_of_release);
        mImageViewProgressBar = (ProgressBar) findViewById(R.id.iv_detail_page_progress_bar);
        mBackgroundPosterImageView = (ImageView) findViewById(R.id.detail_page_image_view);

        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                if (movieAddedToFav == true) {
                    imageView.setImageResource(R.drawable.ic_heart_outline);
                    Toast.makeText(getBaseContext(),getResources().getString(R.string.removed_from_fav),Toast.LENGTH_SHORT).show();
                    bundle.putInt(AppConstants.getFavStarButtonKey(),AppConstants.getBundleValueRemoveFav());
                    getSupportLoaderManager().initLoader(ID_UPDATE_LOADER,bundle,DetailPage.this);
                    movieAddedToFav = false;
                } else {
                    imageView.setImageResource(R.drawable.ic_heart_fill);
                    Toast.makeText(getBaseContext(),getResources().getString(R.string.added_to_fav),Toast.LENGTH_SHORT).show();
                    bundle.putInt(AppConstants.getFavStarButtonKey(),AppConstants.getBundleValueAddFav());
                    getSupportLoaderManager().initLoader(ID_UPDATE_LOADER,bundle,DetailPage.this);
                    movieAddedToFav = true;
                }
            }
        });

        super.onCreate(savedInstanceState);
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        switch (id) {
            case ID_DETAIL_LOADER:
                Uri mMainUri = Contract.MainMoviesEntry.CONTENT_URI;
                String selection = Contract.MainMoviesEntry.MOVIES_ID + AppConstants.getSelectionEqualQuestionString();
                String[] selectionArgs = {String.valueOf(mMovieId)};
                return new CursorLoader(getApplicationContext(),
                        mMainUri,
                        projection,
                        selection,
                        selectionArgs,
                        null);
            // another loader id for update query
            case ID_UPDATE_LOADER:
                // trying doing this thing in a different thread.
                //always getting 0 despite bundling the value to 1
                int valueFav = args.getInt(AppConstants.getFavStarButtonKey());
                Uri mainUri = Contract.MainMoviesEntry.CONTENT_URI;
                String selections = Contract.MainMoviesEntry.MOVIES_ID + AppConstants.getSelectionEqualQuestionString();
                String[] selectionArgss = {String.valueOf(mMovieId)};
                ContentValues values = new ContentValues();
                values.put(Contract.MainMoviesEntry.MOVIE_IS_FAVOURITE, valueFav);
                int i = getContentResolver().update(mainUri,values,selections,selectionArgss);
                return null;
            default:
                return null;
        }
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
        String voteAverage = String.valueOf(data.getFloat(data.getColumnIndex(Contract.MainMoviesEntry.MOVIE_VOTE_AVERAGE)));
        mRatingsTextView.setText(voteAverage);
        mTitleTextView.setText(data.getString(data.getColumnIndex(Contract.MainMoviesEntry.MOVIE_TITLE)));
        mReleaseDateTextView.setText(data.getString(data.getColumnIndex(Contract.MainMoviesEntry.MOVIE_RELEASE_DATE)));
        String backgroundImagePath = data.getString(data.getColumnIndex(Contract.MainMoviesEntry.MOVIE_BACK_POSTER_PATH));


        boolean isBackgroundPoster = true;


        // setting the fav image
        int is_fav = data.getInt(data.getColumnIndex(Contract.MainMoviesEntry.MOVIE_IS_FAVOURITE));

        if (is_fav == 1) {
            movieAddedToFav = true;
            imageView.setImageResource(R.drawable.ic_heart_fill);
        } else {
            movieAddedToFav = false;
            imageView.setImageResource(R.drawable.ic_heart_outline);
        }


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

    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {

    }

}