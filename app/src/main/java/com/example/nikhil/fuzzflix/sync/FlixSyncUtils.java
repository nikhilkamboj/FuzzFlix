package com.example.nikhil.fuzzflix.sync;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.util.Log;

import com.example.nikhil.fuzzflix.database.Contract;
import com.firebase.jobdispatcher.Constraint;
import com.firebase.jobdispatcher.Driver;
import com.firebase.jobdispatcher.FirebaseJobDispatcher;
import com.firebase.jobdispatcher.GooglePlayDriver;
import com.firebase.jobdispatcher.Job;
import com.firebase.jobdispatcher.Lifetime;
import com.firebase.jobdispatcher.Trigger;

import java.util.concurrent.TimeUnit;

/**
 * Created by nikhil on 06/03/18.
 *
 * handles the FireBaseJobDispatcher
 */

public class FlixSyncUtils {

    private static final int SYNC_INTERVAL_HOURS = 5;
    private static final int SYNC_INTERVAL_SECONDS = (int) TimeUnit.HOURS.toSeconds(SYNC_INTERVAL_HOURS);
    private static final int SYNC_FLEXTIME_SECONDS = SYNC_INTERVAL_SECONDS / 3;

    private static boolean sInitialized;

    private static final String MOVIE_SYNC_TAG = "movie-sync";

    static void scheduleFirebaseJobDispatcher(final Context context) {

        Driver driver = new GooglePlayDriver(context);

        FirebaseJobDispatcher firebaseJobDispatcher = new FirebaseJobDispatcher(driver);

        Job syncJob = firebaseJobDispatcher.newJobBuilder()
                // look for imports in JobService class they must be of FireBase lib
                .setService(FlixFirebaseJobService.class)
                .setTag(MOVIE_SYNC_TAG)
                .setConstraints(Constraint.ON_ANY_NETWORK)
                .setLifetime(Lifetime.FOREVER)
                .setRecurring(true)
                .setTrigger(Trigger.executionWindow(SYNC_INTERVAL_SECONDS, SYNC_INTERVAL_SECONDS+SYNC_FLEXTIME_SECONDS))
                .setReplaceCurrent(true)
                .build();

        firebaseJobDispatcher.schedule(syncJob);
    }


    synchronized public static void initialize(final Context context) {

        if (sInitialized) {
            return;
        }

        // this is done when the app is run first as to initialize the jobScheduler
        sInitialized = true;

        // job scheduler starts here
        Log.v(MOVIE_SYNC_TAG, "scheduler initialized");
        scheduleFirebaseJobDispatcher(context);

        /**
         * now when app is first run scheduler starts after hrs hence initially it needs to
         * have some data therefore we start the Intent service to get data immediately to start
         * filling up data to the db
         */

        /**
         * starting a background thread to query data from table and then if empty starting
         * request to fillup data to db
         */

        Thread checkForEmpty = new Thread(new Runnable() {
            @Override
            public void run() {
                Uri mainContentUri = Contract.MainMoviesEntry.CONTENT_URI;

                String[] projection = {Contract.MainMoviesEntry.MOVIES_ID};

                Cursor cursor = context.getContentResolver().query(mainContentUri,
                        projection,
                        null,
                        null,
                        null);

                if (cursor == null || cursor.getCount() == 0) {
                    startImmediateSync(context);
                }
            }
        });

        checkForEmpty.start();
    }

    public static void startImmediateSync(final Context context) {
        // starting intent service
        Log.v(MOVIE_SYNC_TAG, "starting intent service");
        Intent immediateIntentService = new Intent(context,MovieIntentService.class);
        context.startService(immediateIntentService);
    }


}
