package com.example.android.java;

import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.android.java.utilities.ActivityHelper;

import java.util.Locale;

public class MainActivity extends AppCompatActivity
        implements TextToSpeech.OnInitListener {

    private ScrollView mScroll;
    private TextView mLog, mEditText;

    private TextToSpeech tts;
    private boolean ttsInitialized;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//      Initialize the logging components
        mScroll = (ScrollView) findViewById(R.id.scrollLog);
        mLog = (TextView) findViewById(R.id.tvLog);
        mLog.setText("");

        mEditText = (TextView) findViewById(R.id.searchText);

        tts = new TextToSpeech(getApplicationContext(), this);

    }

    public void onRunBtnClick(View v) {
        String text = mEditText.getText().toString();
        if (text.length() == 0) {
            Toast.makeText(MainActivity.this, "What do you want to say?",
                    Toast.LENGTH_SHORT).show();
        } else {
            displayMessage(text);
            saySomething(text);
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

    @Override
    public void onInit(int status) {
        if (status == TextToSpeech.SUCCESS) {
            int result = tts.setLanguage(Locale.US);

            if (result == TextToSpeech.LANG_MISSING_DATA ||
                    result == TextToSpeech.LANG_NOT_SUPPORTED) {
                displayMessage("This language isn't supported");
            } else {
                ttsInitialized = true;
            }
        } else {
            displayMessage("TTS initialization failed");
        }
    }

    private void saySomething(String speech) {
        if (!ttsInitialized) {
            displayMessage("TextToSpeech wasn't initialized");
            return;
        }

        tts.speak(speech, TextToSpeech.QUEUE_FLUSH, null, "speech");
    }
}