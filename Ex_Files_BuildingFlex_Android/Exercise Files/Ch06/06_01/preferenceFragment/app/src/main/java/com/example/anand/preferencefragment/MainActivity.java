package com.example.anand.preferencefragment;

import android.Manifest;
import android.app.LoaderManager;
import android.app.job.JobInfo;
import android.app.job.JobScheduler;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.Loader;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.os.Build;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.preference.PreferenceManager;
import android.provider.ContactsContract;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.CursorAdapter;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.Toast;

import java.security.Permission;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static android.provider.ContactsContract.*;

public class MainActivity extends AppCompatActivity
        implements android.support.v4.app.LoaderManager.LoaderCallbacks<Cursor> {

    public static final int JOB_ID = 1001;
    private static final int REQUEST_CODE_PERM = 1002;

    private boolean mPermission;
    private ListView mList;
    private CursorAdapter mCursorAdapter;
    public static final String[] PROJECTION ={
            Contacts._ID,
            Contacts.LOOKUP_KEY,
            Contacts.DISPLAY_NAME_PRIMARY
    };
     public static final String[] FROM_COLUMN = {
            Contacts.DISPLAY_NAME_PRIMARY,
    };
    public static final  int[] TO_VIEW = {
            android.R.id.text1
    };

    private Handler mHandler;

    ExecutorService mExecutor;

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mExecutor.shutdown();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mExecutor = Executors.newFixedThreadPool(5);

        mList = findViewById(R.id.listview);



        int permission = ContextCompat.checkSelfPermission
                (this, Manifest.permission.READ_CONTACTS);
        if(permission == PackageManager.PERMISSION_GRANTED){
            mPermission = true;
            Toast.makeText(this, "Permission granted already", Toast.LENGTH_SHORT).show();
            LoadContact();
        } else{
            if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
                this.requestPermissions(new String[]{Manifest.permission.READ_CONTACTS}, REQUEST_CODE_PERM);
            }
        }
        // call the handler and get the main thread
        mHandler = new Handler(Looper.getMainLooper()){
            @Override
            public void handleMessage(Message msg) {
                Bundle arg = msg.getData();
                String message = arg.getString("Handler_msg_key");
                Log.i("handlermessage", "handleMessage: "+message);
            }
        };
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if(requestCode == REQUEST_CODE_PERM && grantResults[0] == PackageManager.PERMISSION_GRANTED){
            mPermission=true;
            Toast.makeText(this, "Permission granted ", Toast.LENGTH_SHORT).show();
            LoadContact();
        }
    }

    private void LoadContact() {
        if(mPermission){
            mCursorAdapter = new SimpleCursorAdapter(this,
                    android.R.layout.simple_list_item_1,null, FROM_COLUMN, TO_VIEW, 0);
            mList.setAdapter(mCursorAdapter);
            getSupportLoaderManager().initLoader(0,null, this);
        }
    }


    public void showClick(MenuItem item) {


    }

    public void schedule(View view) {
                 for(int i =0 ; i<= 10 ; i++){
                     Runnable runnable = new BackgroundTask(i);
                     mExecutor.execute(runnable);
                 }
    }

    @Override
    public android.support.v4.content.Loader<Cursor> onCreateLoader(int id, Bundle args) {
        return new CursorLoader(this, Contacts.CONTENT_URI, PROJECTION,
        null, null, Contacts.DISPLAY_NAME_PRIMARY );
    }

    @Override
    public void onLoadFinished(android.support.v4.content.Loader<Cursor> loader, Cursor data) {
          mCursorAdapter.swapCursor(data);
    }

    @Override
    public void onLoaderReset(android.support.v4.content.Loader<Cursor> loader) {

    }
}
