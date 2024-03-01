package com.main.dietidealsclient.Activity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import androidx.activity.ComponentActivity;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.main.dietidealsclient.Controller.UserProfileController;
import com.main.dietidealsclient.R;
import com.main.dietidealsclient.Utility.LoggedUser;

public class EditProfileActivity extends ComponentActivity {

    private final UserProfileController userProfileController;

    Button updateButton;

    public EditProfileActivity(){
        userProfileController = new UserProfileController();
    }

    @Override
    protected void onCreate(Bundle savedInstance) {
        super.onCreate(savedInstance);
        setContentView(R.layout.activity_edit_profile);
        updateButton = findViewById(R.id.profileEdit_updateBtton);

        LoadProfile();

        updateButton.setOnClickListener(view -> {
            try {
                UpdateProfile();
            } catch (JsonProcessingException | InterruptedException e) {
                throw new RuntimeException(e);
            }
        });
    }

    private void UpdateProfile() throws JsonProcessingException, InterruptedException {
        String name = ((EditText)findViewById(R.id.profileEdit_name)).getText().toString();
        String surname = ((EditText)findViewById(R.id.profileEdit_surname)).getText().toString();
        String bio = ((EditText)findViewById(R.id.profileEdit_bio)).getText().toString();
        if(!name.isEmpty()){
            userProfileController.UpdateUserProfile(name,surname,bio);
        }
    }

    private void LoadProfile(){

        EditText name = findViewById(R.id.profileEdit_name);
        name.setText(userProfileController.getLoggedUser().getNome());

        EditText surname = findViewById(R.id.profileEdit_surname);
        surname.setText(userProfileController.getLoggedUser().getCognome());

        EditText bio = findViewById(R.id.profileEdit_bio);
        bio.setText(userProfileController.getLoggedUser().getShortbio());
    }
}
