package com.example.anand.localdatastore.Adapter;

/**
 * Created by anand on 10/30/2017.
 */
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.preference.PreferenceManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.anand.localdatastore.DetailActivity;
import com.example.anand.localdatastore.Model.DataItem;
import com.example.anand.localdatastore.R;
import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.List;
import java.util.Map;

public class DataItemAdapter extends RecyclerView.Adapter<DataItemAdapter.ViewHolder> {

    public static final String ITEM_ID_KEY = "item_id_key";
    public static final String ITEM_KEY = "item_key";
    private List<DataItem> mItems;
    private Map<String, Bitmap> mBitmap;
    private Context mContext;


    private  SharedPreferences.OnSharedPreferenceChangeListener prefListner;

    public DataItemAdapter(Context context, List<DataItem> items, Map<String, Bitmap> bitmap) {
        this.mContext = context;
        this.mItems = items;
        this.mBitmap = bitmap;
    }

    @Override
    public DataItemAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences(mContext);

       prefListner = new SharedPreferences.OnSharedPreferenceChangeListener(){

           @Override
           public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
               Log.i("Preference", "onSharedPreferenceChanged: "+ key);
           }
       };
       settings.registerOnSharedPreferenceChangeListener(prefListner);

        boolean grid = settings.getBoolean(mContext.getString(R.string.pref_check_box), false);

        int layoutid = grid ? R.layout.grid_layout : R.layout.list_view_item;

        LayoutInflater inflater = LayoutInflater.from(mContext);
        View itemView = inflater.inflate(layoutid, parent, false);
        ViewHolder viewHolder = new ViewHolder(itemView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(DataItemAdapter.ViewHolder holder, int position) {
        final DataItem item = mItems.get(position);

        try {
            holder.tvName.setText(item.getItemName());
//            String imageFile = item.getImage();
//            InputStream inputStream = mContext.getAssets().open(imageFile);
//            Drawable d = Drawable.createFromStream(inputStream, null);

            //Bitmap bitmap = mBitmap.get(item.getItemName());
           // holder.imageView.setImageBitmap(bitmap);

            String imageurl = "http://560057.youcanlearnit.net/services/images/" + item.getImage();
            Picasso.with(mContext).load(imageurl).resize(50, 50).into(holder.imageView);

        } catch (Exception e) {
            e.printStackTrace();
        }
        holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //String itemid = item.getItemId();
                Intent intent = new Intent(mContext, DetailActivity.class);
                intent.putExtra(ITEM_KEY, item);
                mContext.startActivities(new Intent[]{intent});
            }
        });
    }

    @Override
    public int getItemCount() {
        return mItems.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        public TextView tvName;
        public ImageView imageView;
        public  View mView;
        public ViewHolder(View itemView) {
            super(itemView);

            tvName = (TextView) itemView.findViewById(R.id.itemNameText);
            imageView = (ImageView) itemView.findViewById(R.id.imageView);
            mView = itemView;
        }
    }

    private class ImageDownloadTask extends AsyncTask<DataItem, Void, Bitmap> {
        private static final String PHOTOS_BASE_URL =
                "http://560057.youcanlearnit.net/services/images/";
        private DataItem mDataItem;
        private ViewHolder mHolder;

        public void setViewHolder(ViewHolder holder) {
            mHolder = holder;
        }

        @Override
        protected Bitmap doInBackground(DataItem... dataItems) {

            mDataItem = dataItems[0];
            String imageUrl = PHOTOS_BASE_URL + mDataItem.getImage();
            InputStream in = null;

            try {
                in = (InputStream) new URL(imageUrl).getContent();
                return BitmapFactory.decodeStream(in);
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                try {
                    if (in != null) {
                        in.close();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            return null;
        }
    }
}