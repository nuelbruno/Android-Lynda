package com.example.android.java;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ScrollView;
import android.widget.TextView;

import com.example.android.java.utilities.ActivityHelper;
import com.example.android.java.utilities.FileHelper;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity {

    private ScrollView mScroll;
    private TextView mLog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//      Initialize the logging components
        mScroll = (ScrollView) findViewById(R.id.scrollLog);
        mLog = (TextView) findViewById(R.id.tvLog);
        mLog.setText("");

    }

    public void onRunBtnClick(View v) {
        String json = FileHelper.readFileFromAssets(this, "data.json");
//        mLog.setText(json);

        try {
            JSONArray jsonArray = new JSONArray(json);
            displayMessage("There are " + jsonArray.length() + " items");

            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject item = (JSONObject) jsonArray.get(i);
                String itemName = item.getString("name");
                double itemPrice = item.getDouble("price");
                StringBuilder builder = new StringBuilder(itemName)
                        .append(" (")
                        .append(itemPrice)
                        .append(")\n");
                mLog.append(builder.toString());
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public void onClearBtnClick(View v) {
        mLog.setText("");
        mScroll.scrollTo(0, mScroll.getBottom());
    }

    public void displayMessage(String message) {
//        mLog.append(message + "\n");
        ActivityHelper.log(this, mLog, message, true);
        mScroll.scrollTo(0, mScroll.getBottom());
    }
}