package com.example.anand.localdatastore;

import android.app.IntentService;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.Nullable;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;

import com.example.anand.localdatastore.Model.DataItem;
import com.example.anand.localdatastore.Utility.HttpHelper;
import com.example.anand.localdatastore.Utility.Mywebservice;
import com.google.gson.Gson;

import java.io.IOException;

import retrofit2.Call;

/**
 * Created by anand on 11/4/2017.
 */

public class MyService extends IntentService {

    private static final String TAG = "MyService";
    public static final String MY_SERVICE_MSG = "my_service_msg";
    public static final String MY_SERVICE_PAYLOAD = "my_serv_payload";


    public MyService() {
        super("My service");
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {


        Mywebservice mywebservice =  Mywebservice.retrofit.create(Mywebservice.class);
        Call<DataItem[]> call = mywebservice.dataitems();

        DataItem[] dataItems;

        try {
            dataItems = call.execute().body();
        } catch (IOException e) {
            e.printStackTrace();
            Log.d(TAG, "onHandleIntent: myservice error return");
            return;
        }

        Intent messageIntent = new Intent(MY_SERVICE_MSG);
        messageIntent.putExtra(MY_SERVICE_PAYLOAD, dataItems);

        LocalBroadcastManager managerbrd =
                LocalBroadcastManager.getInstance(getApplicationContext());
        managerbrd.sendBroadcast(messageIntent);

//        Uri uri = intent.getData();
//        Log.i(TAG, "onHandleIntent: " + uri.toString());
//        String response;
//        try {
//            response = HttpHelper.downloadUrl(uri.toString());
//        } catch (IOException e) {
//            e.printStackTrace();
//            return;
//
//        }
//
//        Gson gson = new Gson();
//        DataItem[] dataItems = gson.fromJson(response, DataItem[].class);



    }

    @Override
    public void onCreate() {
        super.onCreate();
        Log.i(TAG, "onCreate: ");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.i(TAG, "onDestroy: ");
    }
}
