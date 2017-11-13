package com.example.android.java;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ScrollView;
import android.widget.TextView;

import com.example.android.java.utilities.ActivityHelper;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;

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
        String text = readFileFromAssets("lorem_ipsum.txt");
        if (text != null) {
            mLog.setText(text);
        }
    }

    public void onClearBtnClick(View v) {
        mLog.setText("");
        mScroll.scrollTo(0, mScroll.getBottom());
    }

    public void displayMessage(String message) {
        ActivityHelper.log(this, mLog, message, true);
        mScroll.scrollTo(0, mScroll.getBottom());
    }

    private String readFileFromAssets(String filename) {

        BufferedReader reader = null;
        StringBuilder builder = new StringBuilder();

        try {
            reader = new BufferedReader(new InputStreamReader(
                    getAssets().open(filename)));
            String line;
            while ((line = reader.readLine()) != null) {
                builder.append(line).append("\n");
            }
            return builder.toString();
        } catch (FileNotFoundException e) {
            displayMessage("File not found: " + e.getMessage());
        } catch (Exception e) {
            displayMessage(e.getMessage());
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (Exception e) {
                    displayMessage(e.getMessage());
                }
            }
        }

        return null;
    }
}