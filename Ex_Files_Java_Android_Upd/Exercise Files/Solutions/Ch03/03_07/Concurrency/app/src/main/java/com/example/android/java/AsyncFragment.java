package com.example.android.java;

import android.app.Fragment;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;

public class AsyncFragment extends Fragment {

    private ParentActivity parent;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        parent = (ParentActivity)context;
        Log.i("AsyncFragment", "attached to parent");
    }

    public interface ParentActivity {
        void handleTaskUpdate(String msg);
    }

    public void runAsyncTask(String... params) {
        MyTask task = new MyTask();
        task.execute(params);
    }

    class MyTask extends AsyncTask<String, String, Void> {

        @Override
        protected Void doInBackground(String... params) {
            for (String s : params) {
                publishProgress("I got " + s);
                try {
                    Thread.sleep(3000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            return null;
        }

        @Override
        protected void onProgressUpdate(String... values) {
            parent.handleTaskUpdate(values[0]);
        }
    }
}
