package com.quannv.flirtatiouschat.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.quannv.flirtatiouschat.R;
import com.quannv.flirtatiouschat.model.Contacts;
import com.squareup.picasso.Picasso;

import de.hdodenhof.circleimageview.CircleImageView;

public class FindFriendsActivity extends AppCompatActivity {

    private Toolbar mToolbar;
    private RecyclerView findFriendsRecyclerList;
    private DatabaseReference usersRef;

    private FirebaseAuth mAuth;
    private String currentUserID;
    private EditText edtSearch;
    private ImageButton imgBtnSeach;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_find_friends);


        usersRef = FirebaseDatabase.getInstance().getReference().child("Users");
        mAuth = FirebaseAuth.getInstance();
        currentUserID = mAuth.getCurrentUser().getUid();


        findFriendsRecyclerList = (RecyclerView) findViewById(R.id.find_friends_recycler_list);
        findFriendsRecyclerList.setLayoutManager(new LinearLayoutManager(this));


        mToolbar = (Toolbar) findViewById(R.id.find_friends_toolbar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setTitle("Find Friends");

        edtSearch = (EditText) findViewById(R.id.search_friend);
        imgBtnSeach = (ImageButton) findViewById(R.id.find_friend_btn);

        imgBtnSeach.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                findFriendByName();
            }
        });
    }


    @Override
    protected void onStart() {
        super.onStart();
    }

    private void findFriendByName() {
        final String seachText = edtSearch.getText().toString();
        FirebaseRecyclerOptions<Contacts> options =
                new FirebaseRecyclerOptions.Builder<Contacts>()
                        .setQuery(usersRef.orderByChild("name").equalTo(seachText), Contacts.class)
                        .build();
        FirebaseRecyclerAdapter<Contacts, FindFriendViewHolder> adapter =
                new FirebaseRecyclerAdapter<Contacts, FindFriendViewHolder>(options) {
                    @Override
                    protected void onBindViewHolder(@NonNull final FindFriendViewHolder holder, final int position, @NonNull Contacts model) {
                        usersRef.child(getRef(position).getKey()).addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                String userImage = dataSnapshot.child("image").getValue().toString();
                                String userName = dataSnapshot.child("name").getValue().toString();
                                String userStatus = dataSnapshot.child("status").getValue().toString();
                                holder.userName.setText(userName);
                                holder.userStatus.setText(userStatus);
                                holder.btnAccept.setText("Connect");
                                holder.btnCancel.setText("Cancel");
                                Picasso.get().load(userImage).placeholder(R.drawable.profile_image).into(holder.profileImage);

                                holder.itemView.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {
                                        String visit_user_id = getRef(position).getKey();
                                        Intent profileIntent = new Intent(FindFriendsActivity.this, ProfileActivity.class);
                                        profileIntent.putExtra("visit_user_id", visit_user_id);
                                        startActivity(profileIntent);
                                    }
                                });
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {

                            }
                        });
                    }

                    @NonNull
                    @Override
                    public FindFriendViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
                        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.users_display_layout, viewGroup, false);
                        FindFriendViewHolder viewHolder = new FindFriendViewHolder(view);
                        return viewHolder;
                    }
                };

        findFriendsRecyclerList.setAdapter(adapter);

        adapter.startListening();
    }


    public static class FindFriendViewHolder extends RecyclerView.ViewHolder {
        TextView userName, userStatus;
        CircleImageView profileImage;
        Button btnAccept, btnCancel;

        public FindFriendViewHolder(@NonNull View itemView) {
            super(itemView);

            userName = itemView.findViewById(R.id.user_profile_name);
            userStatus = itemView.findViewById(R.id.user_status);
            profileImage = itemView.findViewById(R.id.users_profile_image);
            btnAccept = itemView.findViewById(R.id.request_accept_btn);
            btnCancel = itemView.findViewById(R.id.request_cancel_btn);
        }
    }
}
