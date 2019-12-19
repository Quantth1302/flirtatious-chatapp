package com.quannv.flirtatiouschat.fragment;


import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.text.style.UnderlineSpan;
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

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.quannv.flirtatiouschat.R;
import com.quannv.flirtatiouschat.activity.FindFriendsActivity;
import com.quannv.flirtatiouschat.activity.ProfileActivity;
import com.quannv.flirtatiouschat.adapter.HomeAdapter;
import com.quannv.flirtatiouschat.model.User;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends Fragment {

    private GridView gridView;
    private View homeView;
    private DatabaseReference UsersRef;
    private FirebaseAuth mAuth;
    private String currentUserID;

    private ArrayList<User> userList;

    private FloatingActionButton btnRemove;

    public HomeFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        userList = new ArrayList<>();

        mAuth = FirebaseAuth.getInstance();
        currentUserID = mAuth.getCurrentUser().getUid();

        UsersRef = FirebaseDatabase.getInstance().getReference().child("Users");
        UsersRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot dsp : dataSnapshot.getChildren()) {
                    if (!dsp.child("name").exists()) continue;
                    String address = dsp.child("address").getValue().toString();
                    int age = Integer.valueOf(dsp.child("age").getValue().toString());
                    String education = dsp.child("education").getValue().toString();
                    String image = dsp.child("image").getValue().toString();
                    String name = dsp.child("name").getValue().toString();
                    String relationship = dsp.child("relationship").getValue().toString();
                    String status = dsp.child("status").getValue().toString();
                    String uid = dsp.child("uid").getValue().toString();
                    if (uid.equals(currentUserID)) continue;
                    userList.add(new User(uid, name, status, image, address, education, relationship, age));
                }

                gridView = (GridView) homeView.findViewById(R.id.grid_home);
                gridView.setAdapter(new HomeAdapter(getContext(), userList));

                gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        String visit_user_id = userList.get(position).getUid();
                        Intent profileIntent = new Intent(getContext(), ProfileActivity.class);
                        profileIntent.putExtra("visit_user_id", visit_user_id);
                        startActivity(profileIntent);
                    }
                });
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                System.out.println("The read failed: " + databaseError.getCode());
            }
        });

        homeView =  inflater.inflate(R.layout.fragment_home, container, false);
        return homeView;
    }

//    private void getAllUser() {
//        UsersRef = FirebaseDatabase.getInstance().getReference().child("Users");
//        UsersRef.addListenerForSingleValueEvent(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                for (DataSnapshot dsp : dataSnapshot.getChildren()) {
//                    String address = dsp.child("address").getValue().toString();
//                    int age = Integer.valueOf(dsp.child("age").getValue().toString());
//                    String education = dsp.child("education").getValue().toString();
//                    String image = dsp.child("image").getValue().toString();
//                    String name = dsp.child("name").getValue().toString();
//                    String relationship = dsp.child("relationship").getValue().toString();
//                    String status = dsp.child("status").getValue().toString();
//                    String uid = dsp.child("uid").getValue().toString();
//                    userList.add(new User(uid, name, status, image, address, education, relationship, age));
//                }
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError databaseError) {
//                System.out.println("The read failed: " + databaseError.getCode());
//            }
//        });
//    }


}
