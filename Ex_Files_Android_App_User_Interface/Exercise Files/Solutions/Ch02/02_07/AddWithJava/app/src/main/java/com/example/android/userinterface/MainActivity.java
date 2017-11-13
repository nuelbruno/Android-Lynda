package com.example.android.userinterface;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    ViewGroup buttonContainer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        buttonContainer = (ViewGroup) findViewById(R.id.buttonContainer);

    }

    public void clickHandler(View view) {

        Button button = new Button(this);
        button.setText(R.string.click_me);
        buttonContainer.addView(button);

    }
}
