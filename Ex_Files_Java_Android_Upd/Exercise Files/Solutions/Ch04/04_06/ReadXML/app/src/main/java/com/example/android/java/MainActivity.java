package com.example.android.java;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ScrollView;
import android.widget.TextView;

import com.example.android.java.utilities.ActivityHelper;
import com.example.android.java.utilities.FileHelper;
import com.example.android.java.utilities.MyPullParser;

import java.util.List;

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
        String xml = FileHelper.readFileFromAssets(this, "data.xml");
//        mLog.setText(xml);

        MyPullParser parser = new MyPullParser();
        List<Product> products = parser.parseXML(xml);
        mLog.append("There are " + products.size() + " products\n");

        for (Product product:products) {
            StringBuilder builder = new StringBuilder(product.getName())
                    .append(" (")
                    .append(product.getPrice())
                    .append(")\n");
            mLog.append(builder.toString());
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