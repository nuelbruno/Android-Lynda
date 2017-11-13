package com.example.android.java;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ScrollView;
import android.widget.TextView;

import com.example.android.java.utilities.ActivityHelper;

public class MainActivity extends AppCompatActivity
implements SensorEventListener{

    private ScrollView mScroll;
    private TextView mLog;

    private SensorManager sensorManager;
    private Sensor accelerometer;

    private long lastUpdate = 0;
    private float lastX, lastY, lastZ;
    private static final int SHAKE_THRESHOLD = 832;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//      Initialize the logging components
        mScroll = (ScrollView) findViewById(R.id.scrollLog);
        mLog = (TextView) findViewById(R.id.tvLog);
        mLog.setText("");

        sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        accelerometer = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);

    }

    @Override
    protected void onResume() {
        super.onResume();
        sensorManager.registerListener(this, accelerometer, SensorManager.SENSOR_DELAY_NORMAL);
    }

    @Override
    protected void onPause() {
        super.onPause();
        sensorManager.unregisterListener(this);
    }

    public void onRunBtnClick(View v) {
        displayMessage("Running code!");
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
    public void onSensorChanged(SensorEvent event) {
        Sensor sensor = event.sensor;
        if (sensor.getType() == Sensor.TYPE_ACCELEROMETER) {
            float x = event.values[0];
            float y = event.values[1];
            float z = event.values[2];

            long currentTime = System.currentTimeMillis();

            if ((currentTime - lastUpdate) > 300) {
                long diffTime = (currentTime - lastUpdate);
                lastUpdate = currentTime;

                float speed = Math.abs(x + y + z - lastX - lastY - lastZ) / diffTime * 10000;

                if (speed > SHAKE_THRESHOLD) {
                    displayMessage("Stop shaking me!");
                }

                lastX = x;
                lastY = y;
                lastZ = z;
            }
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }
}