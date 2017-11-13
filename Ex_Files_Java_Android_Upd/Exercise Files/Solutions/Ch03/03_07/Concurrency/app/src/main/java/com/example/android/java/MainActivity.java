package com.example.android.java;

import android.app.FragmentManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ScrollView;
import android.widget.TextView;

import com.example.android.java.utilities.ActivityHelper;

public class MainActivity extends AppCompatActivity
 implements BlankFragment.OnFragmentInteractionListener,
    AsyncFragment.ParentActivity {

    private static final String FRAGMENT_TAG = "FRAGMENT_TAG";
    private ScrollView mScroll;
    private TextView mLog;
    private AsyncFragment mFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//      Initialize the logging components
        mScroll = (ScrollView) findViewById(R.id.scrollLog);
        mLog = (TextView) findViewById(R.id.tvLog);
        mLog.setText("");

        FragmentManager manager = getFragmentManager();
        mFragment = (AsyncFragment)manager.findFragmentByTag(FRAGMENT_TAG);
        if (mFragment == null) {
            mFragment = new AsyncFragment();
            manager.beginTransaction().add(mFragment, FRAGMENT_TAG).commit();
        }
    }

    public void onRunBtnClick(View v) {
        mFragment.runAsyncTask("Chocolate", "Vanilla", "Strawberry");
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

    @Override
    public void onFragmentInteraction() {
        ActivityHelper.log(this, mLog, "Fragment button pressed!", true);
    }

    @Override
    public void handleTaskUpdate(String message) {
        ActivityHelper.log(this, mLog, message, true);
    }
}