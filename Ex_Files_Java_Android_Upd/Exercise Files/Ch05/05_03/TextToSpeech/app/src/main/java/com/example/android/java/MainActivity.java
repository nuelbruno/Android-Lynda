package com.example.android.java;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.android.java.utilities.ActivityHelper;

public class MainActivity extends AppCompatActivity{

    private ScrollView mScroll;
    private TextView mLog, mEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//      Initialize the logging components
        mScroll = (ScrollView) findViewById(R.id.scrollLog);
        mLog = (TextView) findViewById(R.id.tvLog);
        mLog.setText("");

        mEditText = (TextView) findViewById(R.id.searchText);

    }

    public void onRunBtnClick(View v) {
        String text = mEditText.getText().toString();
        if (text.length() == 0) {
            Toast.makeText(MainActivity.this, "What do you want to say?",
                    Toast.LENGTH_SHORT).show();
        } else {
            displayMessage(text);
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

}