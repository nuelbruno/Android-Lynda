package com.example.anand.localdatastore;

import android.content.AsyncTaskLoader;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.preference.PreferenceManager;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.example.anand.localdatastore.Adapter.DataItemAdapter;
import com.example.anand.localdatastore.Model.DataItem;
import com.example.anand.localdatastore.Sample.SampleData;
import com.example.anand.localdatastore.Utility.Mywebservice;
import com.example.anand.localdatastore.Utility.NetworkHelper;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Map<String, Bitmap>> {

    private static final String JSON_URL = "http://560057.youcanlearnit.net/services/json/itemsfeed.php";

    private static final int SIGNIN_REQUEST = 1001;
    public static final String MY_GLOBAL_PREF = "my_global_pref" ;
    List<DataItem> listItem = SampleData.dataItemList;

    List<DataItem> listItems;
    DrawerLayout mDrawerLayout;
    ListView mListview;
    String[] category;
    DataItemAdapter mItemAdapter;
    RecyclerView mRecyclerView;
    Map<String, Bitmap> mBitmap;


    private BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            DataItem[] dataItems = (DataItem[]) intent.getParcelableArrayExtra(MyService.MY_SERVICE_PAYLOAD);
            listItems = Arrays.asList(dataItems);
           // displayDataItems(null);
            //getSupportLoaderManager().initLoader(0,null, MainActivity.this).forceLoad();
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        boolean hasNetwork = NetworkHelper.hasNetworkConnection(this);
        if(hasNetwork) {
//            Intent intent = new Intent(this, MyService.class);
//            intent.setData(Uri.parse(JSON_URL));
//            startService(intent);

            requestdata();
        }

        LocalBroadcastManager.getInstance(getApplicationContext()).registerReceiver(broadcastReceiver,
                new IntentFilter(MyService.MY_SERVICE_MSG));

        mDrawerLayout = findViewById(R.id.draw_layout);
        category = getResources().getStringArray(R.array.category);
        mListview = findViewById(R.id.left_draw);
        mListview.setAdapter(new ArrayAdapter<>(this, R.layout.drawer_list_item, category));

        mListview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String categorySet = category[position];
                Toast.makeText(MainActivity.this, "You chose " + categorySet,
                        Toast.LENGTH_SHORT).show();
                mDrawerLayout.closeDrawer(mListview);
               // displayDataItems(categorySet);
                requestdata(categorySet);
            }
        });

        //dataSource = new DataSource(this);
       // dataSource.Open();
      //  dataSource.seedDatabase(listItem, this);
       // Toast.makeText(this, "Database created" , Toast.LENGTH_LONG).show();

        SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences(this);
        boolean grid = settings.getBoolean(getString(R.string.pref_check_box), false);
        mRecyclerView = findViewById(R.id.rvItems);
        if(grid){
            mRecyclerView.setLayoutManager(new GridLayoutManager(this, 3));
        }

    }

    private void displayDataItems(String cat) {
        if(listItems != null) {
            mItemAdapter = new DataItemAdapter(this, listItems, mBitmap);
            mRecyclerView.setAdapter(mItemAdapter);
        }
    }


    @Override
    protected void onResume() {
        super.onResume();
       // dataSource.Open();
    }

    @Override
    protected void onPause() {
        super.onPause();
       // dataSource.Close();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_item, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        LocalBroadcastManager.getInstance(getApplicationContext()).unregisterReceiver(broadcastReceiver);
    }

    //new Intent[]{intent}
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.action_signin:
                Intent intent = new Intent(this, LoginActivity.class);
                startActivityForResult(intent, SIGNIN_REQUEST);
            case R.id.action_settings:
                Intent settingsIntent = new Intent(this, PrefsActivity.class);
                startActivity(settingsIntent);
            case R.id.action_all_items:
                displayDataItems(null);
                return true;
            case R.id.action_choose_category:
                mDrawerLayout.openDrawer(mListview);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void requestdata(){

//        Intent intent = new Intent(this, MyService.class);
//        startService(intent);
        Mywebservice mywebservice =  Mywebservice.retrofit.create(Mywebservice.class);
        Call<DataItem[]> call = mywebservice.dataitems();
        sendRequest(call);

    }

    private  void requestdata(String category){
        Mywebservice mywebservice =  Mywebservice.retrofit.create(Mywebservice.class);
        Call<DataItem[]> call = mywebservice.dataitems(category);
        sendRequest(call);
    }

    private void sendRequest(Call<DataItem[]> call) {
        call.enqueue(new Callback<DataItem[]>() {
            @Override
            public void onResponse(Call<DataItem[]> call, Response<DataItem[]> response) {
                DataItem[] dataItems = response.body();
                listItems = Arrays.asList(dataItems);
                //
               displayDataItems(null);
            }

            @Override
            public void onFailure(Call<DataItem[]> call, Throwable t) {

            }
        });
    }



    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && requestCode == SIGNIN_REQUEST) {
            String email = data.getStringExtra(LoginActivity.EMAIL_ID);
            Toast.makeText(this, "You signed in as " + email, Toast.LENGTH_SHORT).show();

            SharedPreferences.Editor editor =
                    getSharedPreferences(MY_GLOBAL_PREF, MODE_PRIVATE).edit();
            editor.putString(LoginActivity.EMAIL_ID, email);
            editor.apply();
        }


    }

    @Override
    public android.support.v4.content.Loader<Map<String, Bitmap>> onCreateLoader(int id, Bundle args) {
        return new ImageDownloader(this, listItems);
    }


    @Override
    public void onLoadFinished(android.support.v4.content.Loader<Map<String, Bitmap>> loader, Map<String, Bitmap> data) {
        mBitmap = data;
        //displayDataItems(null);
    }

    @Override
    public void onLoaderReset(Loader<Map<String, Bitmap>> loader) {

    }

    private static class ImageDownloader
            extends android.support.v4.content.AsyncTaskLoader<Map<String, Bitmap>> {

        private static final String PHOTO_BASE_URL = "http://560057.youcanlearnit.net/services/images/";

        private static List<DataItem> mItemlist;

        public ImageDownloader(Context context, List<DataItem> itemlist) {
            super(context);
            mItemlist = itemlist;
        }

        @Override
        public Map<String, Bitmap> loadInBackground() {
            Map<String, Bitmap> bitmapMap = new HashMap<>();
            for (DataItem items: mItemlist) {
                String imageurl = PHOTO_BASE_URL + items.getImage();

                InputStream in = null;
                try {
                    in = (InputStream) new URL(imageurl).getContent();
                    Bitmap bitmap = BitmapFactory.decodeStream(in);
                    bitmapMap.put(items.getItemName(), bitmap);
                } catch (IOException e) {
                    e.printStackTrace();
                } finally {
                    try {
                        in.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }

            }
            return bitmapMap;
        }
    }






   /*
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

   @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_signin:
                Intent intent = new Intent(this, SigninActivity.class);
                startActivityForResult(intent, SIGNIN_REQUEST);
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK && requestCode == SIGNIN_REQUEST) {
            String email = data.getStringExtra(SigninActivity.EMAIL_KEY);
            Toast.makeText(this, "You signed in as " + email, Toast.LENGTH_SHORT).show();
        }

    }*/
}
