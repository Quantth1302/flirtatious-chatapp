package com.quannv.flirtatiouschat.fragment;


import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.quannv.flirtatiouschat.R;
import com.quannv.flirtatiouschat.activity.FindFriendsActivity;
import com.quannv.flirtatiouschat.activity.LoginActivity;
import com.quannv.flirtatiouschat.activity.MainActivity;
import com.quannv.flirtatiouschat.activity.SettingsActivity;
import com.quannv.flirtatiouschat.adapter.OptionAdapter;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;

/**
 * A simple {@link Fragment} subclass.
 */
public class OptionsFragment extends Fragment {

    private View optionView;
    private RecyclerView options;
    private OptionAdapter mAdapter;

    private FirebaseUser currentUser;
    private FirebaseAuth mAuth;
    private DatabaseReference rootRef;
    private String currentUserID;

    public static final int PROFILE=0;
    public static final int FIND_FRIEND=1;
    public static final int LOGOUT=2;



    public OptionsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        optionView = inflater.inflate(R.layout.fragment_options, container, false);
        options = (RecyclerView) optionView.findViewById(R.id.options_list);
        options.setLayoutManager(new LinearLayoutManager(getContext()));
        buildRecyclerView();

        mAuth = FirebaseAuth.getInstance();
        currentUser = mAuth.getCurrentUser();
        currentUserID = mAuth.getCurrentUser().getUid();
        rootRef = FirebaseDatabase.getInstance().getReference();
        return optionView;
    }

    public void buildRecyclerView() {
//        mRecyclerView = findViewById(R.id.recyclerView);
        options.setHasFixedSize(true);
        mAdapter = new OptionAdapter();
        options.setAdapter(mAdapter);

        mAdapter.setOnItemClickListener(new OptionAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                if (position==PROFILE) {
                    SendUserToSettingsActivity();
                }
                if (position==FIND_FRIEND) {
                    SendUserToFindFriendsActivity();
                }
                if (position==LOGOUT) {
                    updateUserStatus("offline");
                    mAuth.signOut();
                    SendUserToLoginActivity();
                }
            }
        });
    }

    private void SendUserToSettingsActivity() {
        Intent settingsIntent = new Intent(getContext(), SettingsActivity.class);
        startActivity(settingsIntent);
    }

    private void SendUserToFindFriendsActivity() {
        Intent findFriendsIntent = new Intent(getContext(), FindFriendsActivity.class);
        startActivity(findFriendsIntent);
    }

    private void SendUserToLoginActivity() {
        Intent loginIntent = new Intent(getContext(), LoginActivity.class);
        loginIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(loginIntent);

    }

    private void updateUserStatus(String state) {
        String saveCurrentTime, saveCurrentDate;

        Calendar calendar = Calendar.getInstance();

        SimpleDateFormat currentDate = new SimpleDateFormat("MMM dd, yyyy");
        saveCurrentDate = currentDate.format(calendar.getTime());

        SimpleDateFormat currentTime = new SimpleDateFormat("hh:mm a");
        saveCurrentTime = currentTime.format(calendar.getTime());

        HashMap<String, Object> onlineStateMap = new HashMap<>();
        onlineStateMap.put("time", saveCurrentTime);
        onlineStateMap.put("date", saveCurrentDate);
        onlineStateMap.put("state", state);

        rootRef.child("Users").child(currentUserID).child("userState")
                .updateChildren(onlineStateMap);
    }

}
