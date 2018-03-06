package com.example.nikhil.fuzzflix.sync;

import android.content.Context;
import android.os.AsyncTask;

import com.firebase.jobdispatcher.JobParameters;
import com.firebase.jobdispatcher.JobService;

/**
 * Created by nikhil on 06/03/18.
 */

public class FlixFirebaseJobService extends JobService {

    private AsyncTask<Void, Void,Void> mFetchMovieTask;

    @Override
    public boolean onStartJob(final JobParameters jobParameters) {

        mFetchMovieTask = new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... params) {
                Context context = getApplicationContext();
                FlixSyncTask.syncMovie(context);
                jobFinished(jobParameters, false);
                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                jobFinished(jobParameters,false);
            }
        };

        mFetchMovieTask.execute();
        return false;
    }

    @Override
    public boolean onStopJob(JobParameters params) {
        if (mFetchMovieTask == null) {
            mFetchMovieTask.cancel(true);
        }
        return true;
    }
}
