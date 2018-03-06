package com.example.nikhil.fuzzflix.sync;

import android.app.job.JobParameters;
import android.app.job.JobService;

/**
 * Created by nikhil on 06/03/18.
 */

public class FlixFirebaseJobService extends JobService {
    @Override
    public boolean onStartJob(JobParameters params) {
        return false;
    }

    @Override
    public boolean onStopJob(JobParameters params) {
        return false;
    }
}
