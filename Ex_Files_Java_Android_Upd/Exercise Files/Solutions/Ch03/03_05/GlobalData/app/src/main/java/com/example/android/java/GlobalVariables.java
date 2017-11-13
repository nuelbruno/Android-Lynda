package com.example.android.java;

import android.app.Application;

public class GlobalVariables extends Application {

    private String textToDisplay;

    public String getTextToDisplay() {
        return textToDisplay;
    }

    public void setTextToDisplay(String textToDisplay) {
        this.textToDisplay = textToDisplay;
    }

}
