package com.example.android.java;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ScrollView;
import android.widget.TextView;

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

        findViewById(R.id.btnRun).setOnClickListener((View v) -> {
            
        });
        
        findViewById(R.id.btnRun).setOnClickListener((View v) -> {
            Log.i("MainActivity", "onClick: " +
                    getResources().getResourceName(v.getId()));
            runCode();
        });

        Button clearButton = (Button) findViewById(R.id.btnClear);
        clearButton.setOnClickListener((View v) -> {
            Log.i("MainActivity", "onClick: " +
                    getResources().getResourceName(v.getId()));
            clearOutput();
        });

        mLog.setText("");
    }

    public void runCode() {
        displayMessage("Running code!");
    }

    public void clearOutput() {
        mLog.setText("");
        mScroll.scrollTo(0, mScroll.getBottom());
    }

    public void displayMessage(String message) {
        mLog.append(message + "\n");
        mScroll.scrollTo(0, mScroll.getBottom());
    }
}