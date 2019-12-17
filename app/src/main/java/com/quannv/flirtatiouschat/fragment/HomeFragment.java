package com.quannv.flirtatiouschat.fragment;


import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.util.DisplayMetrics;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.quannv.flirtatiouschat.R;
import com.quannv.flirtatiouschat.adapter.HomeAdapter;

/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends Fragment {

    private GridView gridView;
    private View homeView;

    static final String[] MOBILE_OS = new String[]{
            "iOS", "Windows", "Blackberry", "Blackberry", "Blackberry", "Blackberry", "Blackberry", "Blackberry"};

    public HomeFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        homeView =  inflater.inflate(R.layout.fragment_home, container, false);
        gridView = (GridView) homeView.findViewById(R.id.grid_home);
        gridView.setAdapter(new HomeAdapter(getContext(), MOBILE_OS));

        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(
                        getContext(),"oki", Toast.LENGTH_SHORT).show();
            }
        });
        return homeView;
    }


}
