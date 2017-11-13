package com.example.anand.preferencefragment;

import android.util.Log;

/**
 * Created by anand on 11/12/2017.
 */

public class BackgroundTask implements Runnable {

    private int mNumThread;

    public BackgroundTask(int mNumThread) {
        this.mNumThread = mNumThread;
    }

    @Override
    public void run() {
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        Log.i("BG-Thread", "run: " + mNumThread);

    }
}
