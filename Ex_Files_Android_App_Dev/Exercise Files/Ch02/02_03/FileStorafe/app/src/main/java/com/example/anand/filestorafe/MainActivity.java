package com.example.anand.filestorafe;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

public class MainActivity extends AppCompatActivity {

    private static final int REQUEST_PERMISSION_WRITE = 1001 ;
    TextView textview;

    public static final String FILE_NAME = "test_file_name.txt";
    private boolean permissionGranted;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textview = findViewById(R.id.outputTxt);
        textview.setText("Started.....");
    }

    private File getFile(){
        return new File(
                Environment.getDownloadCacheDirectory(), FILE_NAME
        );
    }

    public void DeleteEvent(View view) {
        if(!permissionGranted){
            checkPermissions();
            return;
        }

        File file = getFile();
        if(file.exists()){
            file.delete();
            Toast.makeText(this, "File is deleted", Toast.LENGTH_SHORT).show();
        }else {
            Toast.makeText(this, "File is Doesnt exist", Toast.LENGTH_SHORT).show();
        }
    }

    public void ReadEvent(View view) {
        if(!permissionGranted){
            checkPermissions();
            return;
        }
    }

    public void createEvent(View view) {

        if(!permissionGranted){
            checkPermissions();
            return;
        }

        String contentStr = getString(R.string.string_for_file);
        FileOutputStream fileInputStream = null;
        File file = getFile();

        try {
            fileInputStream = new FileOutputStream(file);
            fileInputStream.write(contentStr.getBytes());
            Toast.makeText(this, "FIle is written to storage" , Toast.LENGTH_SHORT).show();
        } catch (IOException e) {
            e.printStackTrace();
            Toast.makeText(this, "Exception:" + e.getMessage() , Toast.LENGTH_SHORT).show();
        } finally {
            try {
                fileInputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    /* Checks if external storage is available for read and write */
    public boolean isExternalStorageWritable() {
        String state = Environment.getExternalStorageState();
        return Environment.MEDIA_MOUNTED.equals(state);
    }

    /* Checks if external storage is available to at least read */
    public boolean isExternalStorageReadable() {
        String state = Environment.getExternalStorageState();
        return (Environment.MEDIA_MOUNTED.equals(state) ||
                Environment.MEDIA_MOUNTED_READ_ONLY.equals(state));
    }

    // Initiate request for permissions.
    private boolean checkPermissions() {

        if (!isExternalStorageReadable() || !isExternalStorageReadable()) {
            Toast.makeText(this, "This app only works on devices with usable external storage",
                    Toast.LENGTH_SHORT).show();
            return false;
        }

        int permissionCheck = ContextCompat.checkSelfPermission(this,
                Manifest.permission.WRITE_EXTERNAL_STORAGE);
        if (permissionCheck != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                    REQUEST_PERMISSION_WRITE);
            return false;
        } else {
            return true;
        }
    }

    // Handle permissions result
    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           @NonNull String permissions[],
                                           @NonNull int[] grantResults) {
        switch (requestCode) {
            case REQUEST_PERMISSION_WRITE:
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    permissionGranted = true;
                    Toast.makeText(this, "External storage permission granted",
                            Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(this, "You must grant permission!", Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }
}
