package com.main.dietidealsclient.Activity;

import android.content.Intent;
import android.os.Bundle;

import androidx.activity.ComponentActivity;

import com.main.dietidealsclient.Controller.UserProfileController;
import com.main.dietidealsclient.Model.Utente;
import com.main.dietidealsclient.R;

public class HomeCompratoreActivity extends ComponentActivity {
    @Override
    protected void onCreate(Bundle savedInstance) {
        super.onCreate(savedInstance);
        setContentView(R.layout.activity_home_compratore);
        inizializeProfile();
    }

    private void inizializeProfile() {
        UserProfileController userProfileController = UserProfileController.getInstance();
        Utente loggedUser = userProfileController.getLoggedUser();
        if(loggedUser.getNome() == null){
            goToEditProfileActivity();
        }
    }

    private void goToEditProfileActivity() {
        Intent myIntent = new Intent(HomeCompratoreActivity.this, EditProfileActivity.class);
        HomeCompratoreActivity.this.startActivity(myIntent);
        finish();
    }
}
