package com.example.anand.localdatastore.Model;

import android.content.ContentValues;
import android.os.Parcel;
import android.os.Parcelable;

import com.example.anand.localdatastore.DataBase.ItemsTable;

import java.util.UUID;

/**
 * Created by anand on 10/29/2017.
 */

public class DataItem implements Parcelable {



    private  String itemId;
    private  String itemName;
    private  String itemDescription;
    private  String category;
    private  int sortPosition;
    private  double price;
    private  String image;

    public DataItem() {
    }
//category
    public DataItem(String itemId, String itemName, String category, String itemDescription, int sortPosition, double price, String image) {

        if(itemId == null){
            itemId = UUID.randomUUID().toString();
        }

        this.itemId = itemId;
        this.itemName = itemName;
        this.itemDescription = itemDescription;
        this.category = category;
        this.sortPosition = sortPosition;
        this.price = price;
        this.image = image;
    }

    public String getItemId() {
        return itemId;
    }

    public void setItemId(String itemId) {
        this.itemId = itemId;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public String getItemDescription() {
        return itemDescription;
    }

    public void setItemDescription(String itemDescription) {
        this.itemDescription = itemDescription;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public int getSortPosition() {
        return sortPosition;
    }

    public void setSortPosition(int sortPosition) {
        this.sortPosition = sortPosition;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public ContentValues toValues(){
        ContentValues values = new ContentValues(7);

        values.put(ItemsTable.COLUMN_ID, itemId);
        values.put(ItemsTable.COLUMN_NAME, itemName);
        values.put(ItemsTable.COLUMN_DESCRIPTION, itemDescription);
        values.put(ItemsTable.COLUMN_CATEGORY, category);
        values.put(ItemsTable.COLUMN_POSITION, sortPosition);
        values.put(ItemsTable.COLUMN_PRICE, price);
        values.put(ItemsTable.COLUMN_IMAGE, image);

        return values;
    }

    @Override
    public String toString() {
        return "DataItem{" +
                "itemId='" + itemId + '\'' +
                ", itemName='" + itemName + '\'' +
                ", itemDescription='" + itemDescription + '\'' +
                ", category='" + category + '\'' +
                ", sortPosition=" + sortPosition +
                ", price=" + price +
                ", image='" + image + '\'' +
                '}';
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.itemId);
        dest.writeString(this.itemName);
        dest.writeString(this.itemDescription);
        dest.writeString(this.category);
        dest.writeInt(this.sortPosition);
        dest.writeDouble(this.price);
        dest.writeString(this.image);
    }

    protected DataItem(Parcel in) {
        this.itemId = in.readString();
        this.itemName = in.readString();
        this.itemDescription = in.readString();
        this.category = in.readString();
        this.sortPosition = in.readInt();
        this.price = in.readDouble();
        this.image = in.readString();
    }

    public static final Parcelable.Creator<DataItem> CREATOR = new Parcelable.Creator<DataItem>() {
        @Override
        public DataItem createFromParcel(Parcel source) {
            return new DataItem(source);
        }

        @Override
        public DataItem[] newArray(int size) {
            return new DataItem[size];
        }
    };
}
