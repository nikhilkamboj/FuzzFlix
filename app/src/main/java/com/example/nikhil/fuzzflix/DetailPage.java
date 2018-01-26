package com.example.nikhil.fuzzflix;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.nikhil.fuzzflix.constants.AppConstants;
import com.example.nikhil.fuzzflix.utilities.NetworkUtils;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.net.URL;
import java.net.URLDecoder;

public class DetailPage extends AppCompatActivity {


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

        String backgroundImagePath = intent.getStringExtra(AppConstants.getBackgroundPosterPathAttribute());

        String titleOfMovie = intent.getStringExtra(AppConstants.getTitleAttribute());

        String releaseDate = intent.getStringExtra(AppConstants.getReleaseDateAttribute());

        String ratingOfMovie = intent.getStringExtra(AppConstants.getRatingAttribute());

        String movieOverView = intent.getStringExtra(AppConstants.getOverviewAttribute());

        boolean isBackgroundPoster = true;

        URL imageUrl = new NetworkUtils().buildImageUrl(backgroundImagePath,isBackgroundPoster);

        String backgroundImageUrl = null;

        try{
            backgroundImagePath = URLDecoder.decode(imageUrl.toString(),"UTF-8");
        }catch (Exception e){
            e.printStackTrace();
        }

        mTitleTextView = (TextView) findViewById(R.id.tv_title);
        mRatingsTextView = (TextView) findViewById(R.id.tv_ratings);
        mReleaseDateTextView = (TextView) findViewById(R.id.tv_date_of_release);
        mOverViewTextView = (TextView) findViewById(R.id.tv_overview);

        Uri imageUri = Uri.parse(backgroundImagePath);
        Context context = getBaseContext();
        mBackgroundPosterImageView = (ImageView) findViewById(R.id.detail_page_image_view);
        mImageViewProgressBar = (ProgressBar) findViewById(R.id.iv_detail_page_progress_bar);

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

        mTitleTextView.setText(titleOfMovie);
        mReleaseDateTextView.setText(releaseDate);
        mRatingsTextView.setText(ratingOfMovie + R.string.base_rating_value);
        mOverViewTextView.setText(movieOverView);


    }
}