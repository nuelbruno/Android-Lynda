package com.example.anand.localdatastore.Adapter;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.anand.localdatastore.Model.DataItem;
import com.example.anand.localdatastore.R;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

/**
 * Created by anand on 10/29/2017.
 */

public class DataItemAdapterListView extends ArrayAdapter<DataItem> {

    List<DataItem> mDataItem;
    LayoutInflater mInflator;

    public DataItemAdapterListView(@NonNull Context context, @NonNull List<DataItem> objects) {
        super(context, R.layout.list_view_item, objects);

        mDataItem = objects;
        mInflator = LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        if(convertView == null){
            convertView = mInflator.inflate(R.layout.list_view_item, parent, false);
        }

        TextView textView = convertView.findViewById(R.id.itemNameText);
        ImageView imageView = convertView.findViewById(R.id.imageView);

        DataItem item = mDataItem.get(position);

        textView.setText(item.getItemName());
        //imageView.setImageResource(R.drawable.apple_pie);
            InputStream inputStream = null;
        try {
            String imgName = item.getImage();
            inputStream = getContext().getAssets().open(imgName);
            Drawable d = Drawable.createFromStream(inputStream, null);
            imageView.setImageDrawable(d);
        } catch (IOException e) {
            e.printStackTrace();
            if(inputStream != null){
                try {
                    inputStream.close();
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
        }

        return convertView;
    }
}
