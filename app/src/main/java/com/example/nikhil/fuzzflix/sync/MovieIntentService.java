package com.example.nikhil.fuzzflix.sync;

import android.app.IntentService;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.util.Log;

/**
 * Created by nikhil on 06/03/18.
 */

public class MovieIntentService extends IntentService {

    private static final String TAG = MovieIntentService.class.getSimpleName();

    public MovieIntentService() {
        super("MovieIntentService.class");
    }

    /**
     * \runs when their is no data in the db, therefore immediately fills up the db
     * and shows its data to the user
     * @param intent
     */
    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        Log.v(TAG, "intent service  started, immediate syncing starting");
        FlixSyncTask.syncMovie(this);
    }
}
