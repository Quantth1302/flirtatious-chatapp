package com.quannv.flirtatiouschat.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import com.quannv.flirtatiouschat.R;
import com.quannv.flirtatiouschat.model.User;
import com.squareup.picasso.Picasso;
import java.util.ArrayList;

public class HomeAdapter extends BaseAdapter {
    private Context context;

    private ArrayList<User> userList;

    public HomeAdapter(Context context, ArrayList<User> userList) {
        this.context = context;
        this.userList = userList;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        if (convertView == null) {
            convertView = inflater.inflate(R.layout.grid_home, null);
            convertView.setLayoutParams(new GridView.LayoutParams(GridView.AUTO_FIT, getHeightScreen()-300));
        }

        User user = userList.get(position);
        // set value into textview
        TextView txtName = (TextView) convertView.findViewById(R.id.home_name);
        String name = user.getName() + ", " + user.getAge();
        txtName.setText(name);

        TextView txtAddress = (TextView) convertView.findViewById(R.id.home_location);
        txtAddress.setText( "From " + user.getAddress());
        // set image based on selected text
        final ImageView imageView = (ImageView) convertView.findViewById(R.id.grid_item_image);
        Picasso.get().load(user.getImage()).into(imageView);
        return convertView;
    }

    @Override
    public int getCount() {
        return userList.size();
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
        return context.getResources().getDisplayMetrics().heightPixels;
    }

}
