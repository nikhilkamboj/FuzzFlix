package com.example.nikhil.fuzzflix.sync;

import android.app.IntentService;
import android.content.Intent;
import android.support.annotation.Nullable;

/**
 * Created by nikhil on 06/03/18.
 */

public class MovieIntentService extends IntentService {

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

        FlixSyncTask.syncMovie(this);
    }
}
