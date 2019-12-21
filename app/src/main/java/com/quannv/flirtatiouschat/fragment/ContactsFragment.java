package com.quannv.flirtatiouschat.fragment;


import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.quannv.flirtatiouschat.R;
import com.quannv.flirtatiouschat.activity.ChatActivity;
import com.quannv.flirtatiouschat.activity.ProfileActivity;
import com.quannv.flirtatiouschat.model.Contacts;
import com.squareup.picasso.Picasso;

import de.hdodenhof.circleimageview.CircleImageView;


/**
 * A simple {@link Fragment} subclass.
 */
public class ContactsFragment extends Fragment {

    private View contactsView;
    private RecyclerView myContactsList;

    private DatabaseReference contactsRef, usersRef;
    private FirebaseAuth mAuth;
    private String currentUserID;


    LinearLayout messageLinearLayout, unfiendLinearLayout;
    static BottomSheetDialog bottomSheetDialog;

    private static String localSelectionUserId;
    private static String localSelectionUserName;
    private static String localSelectionUserImage;

    CircleImageView profileBottomImage;
    TextView contactStatus, contactName;

    public ContactsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        contactsView = inflater.inflate(R.layout.fragment_contacts, container, false);

        myContactsList = (RecyclerView) contactsView.findViewById(R.id.contacts_list);
        myContactsList.setLayoutManager(new LinearLayoutManager(getContext()));

        mAuth = FirebaseAuth.getInstance();
        currentUserID = mAuth.getCurrentUser().getUid();


        contactsRef = FirebaseDatabase.getInstance().getReference().child("Contacts");
        usersRef = FirebaseDatabase.getInstance().getReference().child("Users");

        createBottomSheetDialog();

        return contactsView;
    }

    @Override
    public void onStart() {
        super.onStart();

        FirebaseRecyclerOptions options =
                new FirebaseRecyclerOptions.Builder<Contacts>()
                        .setQuery(contactsRef.child(currentUserID), Contacts.class)
                        .build();

        final FirebaseRecyclerAdapter<Contacts, contactsViewHolder> adapter
                = new FirebaseRecyclerAdapter<Contacts, contactsViewHolder>(options) {
            @Override
            protected void onBindViewHolder(@NonNull final contactsViewHolder holder, int position, @NonNull Contacts model) {
                final String userIDs = getRef(position).getKey();
                final String[] retImage = {"default_image"};

                usersRef.child(userIDs).addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        if (dataSnapshot.exists()) {
                            if (dataSnapshot.hasChild("image")) {
                                retImage[0] = dataSnapshot.child("image").getValue().toString();
//                                Picasso.get().load(retImage[0]).into(holder.profileImage);
                            }
                            if (dataSnapshot.child("userState").hasChild("state")) {
                                String state = dataSnapshot.child("userState").child("state").getValue().toString();
                                String date = dataSnapshot.child("userState").child("date").getValue().toString();
                                String time = dataSnapshot.child("userState").child("time").getValue().toString();

                                if (state.equals("online")) {
                                    holder.onlineIcon.setVisibility(View.VISIBLE);
                                } else if (state.equals("offline")) {
                                    holder.onlineIcon.setVisibility(View.INVISIBLE);
                                }
                            } else {
                                holder.onlineIcon.setVisibility(View.INVISIBLE);
                            }


                            if (dataSnapshot.hasChild("image")) {
                                String userImage = dataSnapshot.child("image").getValue().toString();
                                String profileName = dataSnapshot.child("name").getValue().toString();
                                String profileStatus = dataSnapshot.child("status").getValue().toString();

                                holder.userName.setText(profileName);
                                holder.userStatus.setText(profileStatus);
                                Picasso.get().load(userImage).placeholder(R.drawable.profile_image).into(holder.profileImage);
                            } else {
                                String profileName = dataSnapshot.child("name").getValue().toString();
                                String profileStatus = dataSnapshot.child("status").getValue().toString();

                                holder.userName.setText(profileName);
                                holder.userStatus.setText(profileStatus);
                            }

                            holder.itemView.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    Intent profileIntent = new Intent(getContext(), ProfileActivity.class);
                                    profileIntent.putExtra("visit_user_id", userIDs);
                                    startActivity(profileIntent);
                                }
                            });

                            final String retName = dataSnapshot.child("name").getValue().toString();

                            holder.moreImage.setVisibility(View.VISIBLE);
                            holder.moreImage.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    localSelectionUserId = userIDs;
                                    localSelectionUserName = retName;
                                    localSelectionUserImage = retImage[0];
                                    Picasso.get().load(retImage[0]).placeholder(R.drawable.profile_image).into(profileBottomImage);
                                    contactName.setText(retName);
                                    contactsRef.child(currentUserID).child(userIDs).child("Date").addValueEventListener(new ValueEventListener() {
                                        @Override
                                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                            String date = dataSnapshot.getValue().toString();
                                            contactStatus.setText("Make friends from " + date);
                                        }

                                        @Override
                                        public void onCancelled(@NonNull DatabaseError databaseError) {

                                        }
                                    });
                                    bottomSheetDialog.show();
                                }
                            });
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });
            }

            @NonNull
            @Override
            public contactsViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
                View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.chats_display_layout, viewGroup, false);
                contactsViewHolder viewHolder = new contactsViewHolder(view);
                return viewHolder;
            }
        };

        myContactsList.setAdapter(adapter);
        adapter.startListening();
    }


    public static class contactsViewHolder extends RecyclerView.ViewHolder {
        TextView userName, userStatus;
        CircleImageView profileImage;
        ImageView onlineIcon;
        ImageButton moreImage;


        public contactsViewHolder(@NonNull View itemView) {
            super(itemView);

            userName = itemView.findViewById(R.id.user_profile_name);
            userStatus = itemView.findViewById(R.id.user_status);
            profileImage = itemView.findViewById(R.id.users_profile_image);
            onlineIcon = (ImageView) itemView.findViewById(R.id.user_online_status);
            moreImage = itemView.findViewById(R.id.moreSheet);
        }
    }

    private void createBottomSheetDialog() {
        if (bottomSheetDialog == null) {
            View view = LayoutInflater.from(getContext()).inflate(R.layout.bottom_contact_sheet, null);
            messageLinearLayout = view.findViewById(R.id.messageLinearLayout);
            unfiendLinearLayout = view.findViewById(R.id.unfriendLinearLayout);

            profileBottomImage = view.findViewById(R.id.contact_profile_image);
            contactName = view.findViewById(R.id.contact_profile_name);
            contactStatus = view.findViewById(R.id.contact_profile_status);

            messageLinearLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent chatIntent = new Intent(getContext(), ChatActivity.class);
                    chatIntent.putExtra("visit_user_id", localSelectionUserId);
                    chatIntent.putExtra("visit_user_name", localSelectionUserName);
                    chatIntent.putExtra("visit_image", localSelectionUserImage);
                    startActivity(chatIntent);
//                    Toast.makeText(getContext(), localSelectionUserId, Toast.LENGTH_SHORT).show();
                    bottomSheetDialog.dismiss();
                }
            });
            unfiendLinearLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    RemoveSpecificContact();
                    bottomSheetDialog.dismiss();
                }
            });

            bottomSheetDialog = new BottomSheetDialog(getContext());
            bottomSheetDialog.setContentView(view);
        }
    }

    private void RemoveSpecificContact() {
        contactsRef.child(localSelectionUserId).removeValue()
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            contactsRef.child(currentUserID).child(localSelectionUserId)
                                    .removeValue()
                                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            if (task.isSuccessful()) {
                                                Toast.makeText(getContext(), "Remove successfully", Toast.LENGTH_SHORT).show();
                                            }
                                        }
                                    });
                        }
                    }
                });
    }

}
