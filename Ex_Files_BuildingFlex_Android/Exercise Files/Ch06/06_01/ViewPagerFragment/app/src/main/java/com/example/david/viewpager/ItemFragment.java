package com.example.david.viewpager;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.text.NumberFormat;

public class ItemFragment extends Fragment {

    public static final String PRODUCT_KEY = "Product_key";
    private Product mProduct;

    public static ItemFragment newInstance(Product product) {

        Bundle args = new Bundle();
        args.putParcelable(PRODUCT_KEY, product);
        ItemFragment fragment = new ItemFragment();
        fragment.setArguments(args);
        return fragment;
    }

    public ItemFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        ViewGroup rootView = (ViewGroup) inflater
                .inflate(R.layout.fragment_detail, container, false);

//      display text and image
        Bundle arg = getArguments();
        if(arg == null) throw new AssertionError();

        mProduct = arg.getParcelable(PRODUCT_KEY);
        if(mProduct == null) throw new AssertionError();

        TextView nameText = (TextView) rootView.findViewById(R.id.nameText);
        nameText.setText(mProduct.getName());

        TextView descriptionText = (TextView) rootView.findViewById(R.id.descriptionText);
        descriptionText.setText(mProduct.getDescription());

        NumberFormat formatter = NumberFormat.getCurrencyInstance();
        String price = formatter.format(mProduct.getPrice());
        TextView priceText = (TextView) rootView.findViewById(R.id.priceText);
        priceText.setText(price);

        String productId = mProduct.getProductId();
        int imageResource = getActivity().getResources()
                .getIdentifier(productId, "drawable", getActivity().getPackageName());
        ImageView iv = (ImageView) rootView.findViewById(R.id.imageView);
        iv.setImageResource(imageResource);

        return rootView;
    }

}
