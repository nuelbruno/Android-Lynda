package com.example.anand.localdatastore.Utility;

import com.example.anand.localdatastore.Model.DataItem;

import retrofit2.Call;
        import retrofit2.Retrofit;
        import retrofit2.converter.gson.GsonConverterFactory;
        import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by anand on 11/5/2017.
 */

public interface Mywebservice {

    String BASE_URL = "http://560057.youcanlearnit.net/";
    String FEED = "services/json/itemsfeed.php";

    Retrofit retrofit = new Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build();

    @GET(FEED)
    Call<DataItem[]> dataitems();

    @GET(FEED)
    Call<DataItem[]> dataitems(@Query("category") String category);
}
