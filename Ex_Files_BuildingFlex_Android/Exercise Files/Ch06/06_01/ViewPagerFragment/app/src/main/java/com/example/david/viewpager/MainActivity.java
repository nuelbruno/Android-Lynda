package com.example.david.viewpager;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    private final List<Product> products = DataProvider.productList;
    private final int numPages = products.size();

    private ViewPager mPagerview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mPagerview = (ViewPager) findViewById(R.id.pager);

        PagerAdapter pagerAdapter = new ViewPagerAdapter(getSupportFragmentManager());
        mPagerview.setAdapter(pagerAdapter);

    }


    private  class ViewPagerAdapter extends FragmentPagerAdapter{

        public ViewPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            return ItemFragment.newInstance(products.get(position));
        }

        @Override
        public int getCount() {
            return numPages;
        }
    }

    @Override
    public void onBackPressed() {
        if(mPagerview.getCurrentItem() == 0) {
            super.onBackPressed();
        } else {
            mPagerview.setCurrentItem(mPagerview.getCurrentItem()-1);
        }
    }
}