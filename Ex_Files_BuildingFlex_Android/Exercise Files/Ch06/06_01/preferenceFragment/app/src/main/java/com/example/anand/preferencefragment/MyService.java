package com.example.anand.preferencefragment;

import android.app.Service;
import android.app.job.JobParameters;
import android.app.job.JobService;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.IBinder;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;

import static android.content.ContentValues.TAG;

public class MyService extends JobService {
    public MyService() {
    }

    @Override
    public boolean onStartJob(final JobParameters params) {
        Log.i(TAG, "onStartJob: " + params.getJobId());
        //jobFinished(params, false);
        Runnable r = new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                Log.i(TAG, "thread completer ");
                LocalBroadcastManager.getInstance(getApplicationContext())
                        .sendBroadcast(new Intent("messageService"));
                jobFinished(params, false);
            }
        };
        Thread thread = new Thread(r);
        thread.start();
        return true;
    }

    @Override
    public boolean onStopJob(JobParameters params) {
        return false;
    }


}
