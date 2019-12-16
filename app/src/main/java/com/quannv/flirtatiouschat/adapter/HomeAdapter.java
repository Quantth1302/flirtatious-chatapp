package com.quannv.flirtatiouschat.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.quannv.flirtatiouschat.R;

public class HomeAdapter extends BaseAdapter {
    private Context context;
    private final String[] mobileValues;

    public HomeAdapter(Context context, String[] mobileValues) {
        this.context = context;
        this.mobileValues = mobileValues;
    }

    //    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View gridView;

        if (convertView == null) {

            gridView = new View(context);

            // get layout from mobile.xml
            gridView = inflater.inflate(R.layout.grid_home, null);

            // set value into textview
            TextView textView = (TextView) gridView
                    .findViewById(R.id.grid_item_label);
            textView.setText(mobileValues[position]);

            // set image based on selected text
            ImageView imageView = (ImageView) gridView
                    .findViewById(R.id.grid_item_image);

            String mobile = mobileValues[position];

            if (mobile.equals("Windows")) {
                imageView.setImageResource(R.drawable.email);
            } else if (mobile.equals("iOS")) {
                imageView.setImageResource(R.drawable.phone);
            } else if (mobile.equals("Blackberry")) {
                imageView.setImageResource(R.drawable.email);
            } else {
                imageView.setImageResource(R.drawable.login_photo);
            }

        } else {
            gridView = (View) convertView;
        }

//        gridView.setBackgroundResource(R.drawable.border);

        return gridView;
    }

    @Override
    public int getCount() {
        return mobileValues.length;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }
}
