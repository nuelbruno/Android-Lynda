package com.example.anand.localdatastore.DataBase;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

import com.example.anand.localdatastore.Model.DataItem;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by anand on 11/3/2017.
 */

public class DataSource {

    private Context  mContext;
    private SQLiteDatabase mDatabase;
    private SQLiteOpenHelper mDbhelper;

    public DataSource(Context context) {
        this.mContext = context;
        mDbhelper = new DBHelper(mContext);
        mDatabase =mDbhelper.getWritableDatabase();
    }

    public void Open(){
        mDatabase = mDbhelper.getWritableDatabase();
    }

    public void Close(){
        mDbhelper.close();
    }

    public DataItem createItem(DataItem items){
        ContentValues values = items.toValues();
        mDatabase.insert(ItemsTable.TABLE_ITEMS, null, values);
        return items;
    }

    public long getDataItemsCount(){
        return DatabaseUtils.queryNumEntries(mDatabase, ItemsTable.TABLE_ITEMS);
    }

    public  void  seedDatabase(List<DataItem> itemlist, Context context){
        long countRows = this.getDataItemsCount();
        if(countRows == 0 ) {
            for (DataItem item : itemlist) {
                try {
                    this.createItem(item);
                } catch (SQLException e) {
                    e.printStackTrace();
                }
                Toast.makeText(context, "New data Inserted" , Toast.LENGTH_LONG).show();
            }
        } else {
            Toast.makeText(context, "Database Already Inserted" , Toast.LENGTH_LONG).show();
        }
    }

    public  List<DataItem> fetchDataFromDB(String cat){
        List<DataItem> mItems = new ArrayList<>();

        Cursor cursor = null;
        if(cat == null) {
            cursor = mDatabase.query(ItemsTable.TABLE_ITEMS, ItemsTable.ALL_COLUMNS,
                    null, null, null, null, ItemsTable.COLUMN_NAME);
        } else {
            String[] category = {cat};
            cursor = mDatabase.query(ItemsTable.TABLE_ITEMS, ItemsTable.ALL_COLUMNS,
                    ItemsTable.COLUMN_CATEGORY + "=?", category, null, null, ItemsTable.COLUMN_NAME);
        }

       while (cursor.moveToNext()){
           DataItem item = new DataItem();
           item.setItemId(cursor.getString(
                   cursor.getColumnIndex(ItemsTable.COLUMN_ID)));
           item.setItemName(cursor.getString(
                   cursor.getColumnIndex(ItemsTable.COLUMN_NAME)));
           item.setItemDescription(cursor.getString(
                   cursor.getColumnIndex(ItemsTable.COLUMN_DESCRIPTION)));
           item.setCategory(cursor.getString(
                   cursor.getColumnIndex(ItemsTable.COLUMN_CATEGORY)));
           item.setSortPosition(cursor.getInt(
                   cursor.getColumnIndex(ItemsTable.COLUMN_ID)));
           item.setPrice(cursor.getDouble(
                   cursor.getColumnIndex(ItemsTable.COLUMN_PRICE)));
           item.setImage(cursor.getString(
                   cursor.getColumnIndex(ItemsTable.COLUMN_IMAGE)));
           mItems.add(item);
       }
        cursor.close();

        return mItems;
    }
}
