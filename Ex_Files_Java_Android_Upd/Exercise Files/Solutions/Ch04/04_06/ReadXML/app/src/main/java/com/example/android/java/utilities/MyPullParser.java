package com.example.android.java.utilities;

import com.example.android.java.Product;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;

public class MyPullParser {

    private static final String LOGTAG = "PULL_PARSER";

    private static final String NAME_TAG = "name";
    private static final String PRICE_TAG = "price";

    private Product mProduct = null;
    List<Product> mProducts = new ArrayList<>();
    private String mTag = null;

    public List<Product> parseXML(String xml) {

        try {
            XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
            factory.setNamespaceAware(true);
            XmlPullParser xpp = factory.newPullParser();

            StringReader reader = new StringReader(xml);
            xpp.setInput(reader);

            int eventType = xpp.getEventType();
            while (eventType != XmlPullParser.END_DOCUMENT) {
                if (eventType == XmlPullParser.START_TAG) {
                    handleStartTag(xpp.getName());
                } else if (eventType == XmlPullParser.END_TAG) {
                    mTag = null;
                } else if (eventType == XmlPullParser.TEXT) {
                    handleText(xpp.getText());
                }
                eventType = xpp.next();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return mProducts;
    }

    private void handleText(String text) {
        String xmlText = text;
        if (mProduct != null && mTag != null) {
            if (mTag.equals(NAME_TAG)) {
                mProduct.setName(xmlText);
            }
            else if (mTag.equals(PRICE_TAG)) {
                double price = Double.parseDouble(xmlText);
                mProduct.setPrice(price);
            }
        }
    }

    private void handleStartTag(String name) {
        if (name.equals("product")) {
            mProduct = new Product();
            mProducts.add(mProduct);
        }
        else {
            mTag = name;
        }
    }

}