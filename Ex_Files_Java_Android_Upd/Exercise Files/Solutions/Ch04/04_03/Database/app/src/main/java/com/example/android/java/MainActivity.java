package com.example.android.java;

import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.android.java.database.MyDatasource;
import com.example.android.java.utilities.ActivityHelper;

public class MainActivity extends AppCompatActivity {

    private ScrollView mScroll;
    private TextView mLog, mSearch;

    private MyDatasource datasource;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//      Initialize the logging components
        mScroll = (ScrollView) findViewById(R.id.scrollLog);
        mLog = (TextView) findViewById(R.id.tvLog);
        mLog.setText("");

        mSearch = (TextView) findViewById(R.id.searchText);

        datasource = new MyDatasource(this);
        datasource.initData();

    }

    public void onRunBtnClick(View v) {
        searchDatabase(v);
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

    public void searchDatabase(View view) {

        String searchString = mSearch.getText().toString();

        if (searchString.length() == 0) {
            Toast.makeText(MainActivity.this, "Enter an integer between 1 and 100",
                    Toast.LENGTH_SHORT).show();
            return;
        }

        int indexValue = new Integer(searchString);

        Cursor cursor = datasource.selectRecord(indexValue);
        if (cursor.moveToNext()) {
            String name = cursor.getString(cursor.getColumnIndex(MyDatasource.PERSON_NAME));
            Toast.makeText(MainActivity.this, "You found " + name, Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(MainActivity.this, "Person not found", Toast.LENGTH_SHORT).show();
        }

    }
}