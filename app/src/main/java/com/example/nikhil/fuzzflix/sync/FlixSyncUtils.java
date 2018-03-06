package com.example.nikhil.fuzzflix.sync;

import android.content.Context;

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



}
