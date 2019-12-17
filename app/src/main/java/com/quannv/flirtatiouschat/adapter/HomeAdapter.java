package com.quannv.flirtatiouschat.adapter;

import android.content.Context;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
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

//        View gridView;

        if (convertView == null) {

//            gridView = new View(context);

            // get layout from mobile.xml
            convertView = inflater.inflate(R.layout.grid_home, null);

            convertView.setLayoutParams(new GridView.LayoutParams(GridView.AUTO_FIT, getHeightScreen()-100));


        }
//        gridView = (View) convertView;
        // set value into textview
//            TextView textView = (TextView) gridView
//                    .findViewById(R.id.grid_item_label);
//            textView.setText(mobileValues[position]);

        // set image based on selected text
        ImageView imageView = (ImageView) convertView
                .findViewById(R.id.grid_item_image);

        String mobile = mobileValues[position];

        if (mobile.equals("Windows")) {
            imageView.setImageResource(R.drawable.login_photo);
        } else if (mobile.equals("iOS")) {
            imageView.setImageResource(R.drawable.signup_photo);
        } else if (mobile.equals("Blackberry")) {
            imageView.setImageResource(R.drawable.login_photo);
        } else {
            imageView.setImageResource(R.drawable.file);
        }


//        gridView.setBackgroundResource(R.drawable.border);

        return convertView;
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

    private int getHeightScreen() {
        //        int width = context.getResources().getDisplayMetrics().widthPixels;
//        gridView.setColumnWidth(width/3);

        return context.getResources().getDisplayMetrics().heightPixels;
    }

}
