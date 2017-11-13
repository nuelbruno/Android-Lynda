package com.example.android.java;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.SparseArray;
import android.view.View;
import android.widget.ScrollView;
import android.widget.TextView;

//import java.util.HashMap;
//import java.util.Map;


public class MainActivity extends AppCompatActivity {

    private ScrollView mScroll;
    private TextView mLog;

//    private Map<Integer, Person> map;
    private SparseArray<Person> sparseArray;

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

//        map = new HashMap<>();
        sparseArray = new SparseArray<>();

        for (int i = 0; i < 10000; i++) {
            Person p = new Person(i, "Person " + i);
//            map.put(i, p);
            sparseArray.append(i, p);
        }
//        displayMessage("There are " + map.size() + " Person objects");
        displayMessage("There are " + sparseArray.size() + " Person objects");
    }

    public void onClearBtnClick(View v) {
        mLog.setText("");
        mScroll.scrollTo(0, mScroll.getBottom());
    }

    public void displayMessage(String message) {
        mLog.append(message + "\n");
//        ActivityHelper.log(this, mLog, message, true);
        mScroll.scrollTo(0, mScroll.getBottom());
    }
}