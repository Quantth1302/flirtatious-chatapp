package com.quannv.flirtatiouschat.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import com.quannv.flirtatiouschat.R;

import de.hdodenhof.circleimageview.CircleImageView;

public class SettingsActivity extends AppCompatActivity {

    private Button btnUpdateAccountSettings;
    private EditText edtUserName, edtUserStatus;
    private CircleImageView imgUserProfile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        init();
    }

    private void init() {

        btnUpdateAccountSettings = (Button) findViewById(R.id.update_settings_button);
        edtUserName = (EditText) findViewById(R.id.set_user_name);
        edtUserStatus = (EditText) findViewById(R.id.set_profile_status);
        imgUserProfile = (CircleImageView) findViewById(R.id.set_profile_image);
    }
}
