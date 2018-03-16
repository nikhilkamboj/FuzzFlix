package com.example.nikhil.fuzzflix.sync;

import android.app.IntentService;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.util.Log;

/**
 * Created by nikhil on 06/03/18.
 */

public class MovieIntentService extends IntentService {
    public static final String DATA_UPDATE_INTENT_FILTER="com.example.nikhil.fuzzflix.ACTION_DATA_UPDATE";
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
        Log.v(TAG, "intent service  Movie Data sync complete");
        Intent intentUpdate = new Intent();
        intentUpdate.setAction(DATA_UPDATE_INTENT_FILTER);
        intentUpdate.addCategory(Intent.CATEGORY_DEFAULT);
//        intentUpdate.putExtra(EXTRA_KEY_UPDATE, i);
        sendBroadcast(intentUpdate);

    }
}
