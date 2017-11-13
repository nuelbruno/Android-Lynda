package com.example.android.userinterface;

import android.graphics.drawable.Drawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.io.InputStream;

public class MainActivity extends AppCompatActivity {

    ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        imageView = (ImageView) findViewById(R.id.foodImage);
        Picasso.with(this).load(R.drawable.apple_pie).into(imageView);
    }

    public void clickHandler(View view) {
//        imageView.setImageResource(R.drawable.artichokes);
        String imageName = "file:///android_asset/artichokes.jpg";
        Picasso.with(this).load(imageName).fit().into(imageView);

//        try (InputStream stream = getAssets().open(imageName)) {
//            Drawable d = Drawable.createFromStream(stream, null);
//            imageView.setImageDrawable(d);
//        } catch (IOException e) {
//            e.printStackTrace();
//        }

    }
}
