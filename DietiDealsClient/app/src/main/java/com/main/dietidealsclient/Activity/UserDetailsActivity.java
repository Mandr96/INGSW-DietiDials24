package com.main.dietidealsclient.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.activity.ComponentActivity;
import androidx.annotation.Nullable;

import com.main.dietidealsclient.R;
import com.main.dietidealsclient.Utility.Logger;

public class UserDetailsActivity extends ComponentActivity {

    ImageView btnBack;
    ImageView imgProfile;
    TextView viewName;
    TextView viewEmail;
    TextView viewBio;
    TextView viewIg;
    TextView viewFb;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_details);

        btnBack = findViewById(R.id.search_btton_back);
        imgProfile = findViewById(R.id.profile_image);
        viewName = findViewById(R.id.user_name);
        viewEmail = findViewById(R.id.email_text);
        viewBio = findViewById(R.id.user_bio);
        viewIg = findViewById(R.id.user_link_ig);
        viewFb = findViewById(R.id.user_link_fb);
        setData();

        btnBack.setOnClickListener(view -> {
            goBack();
        });
    }

    private void setData() {
        //TODO
        //imgProfile
        viewName.setText(getIntent().getStringExtra("NAME"));
        viewEmail.setText(getIntent().getStringExtra("EMAIL"));
        viewBio.setText(getIntent().getStringExtra("BIO"));
        viewIg.setText(getIntent().getStringExtra("IG"));
        viewFb.setText(getIntent().getStringExtra("FB"));
    }

    private void goBack() {
        Logger.log("UserDetailPage","goBack");
        //TODO BUG NEL TORNARE INDIETRO
        Intent myIntent = new Intent(UserDetailsActivity.this, AstaDetailsActivity.class);
        UserDetailsActivity.this.startActivity(myIntent);
        finish();
    }
}
