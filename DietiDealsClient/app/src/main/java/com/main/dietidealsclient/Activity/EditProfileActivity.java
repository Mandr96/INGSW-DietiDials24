package com.main.dietidealsclient.Activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.activity.ComponentActivity;
import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.main.dietidealsclient.Controller.TipoAccount;
import com.main.dietidealsclient.Controller.UserProfileController;
import com.main.dietidealsclient.R;
import com.main.dietidealsclient.Utility.Logger;


import java.io.File;

public class EditProfileActivity extends ComponentActivity {

    private final UserProfileController userProfileController;

    TipoAccount userType;
    Button updateButton;
    ImageView imagePreview;
    ActivityResultLauncher<Intent> resultLauncher;
    Bitmap imageBitmap;

    public EditProfileActivity(){
        userProfileController = new UserProfileController();
    }

    @Override
    protected void onCreate(Bundle savedInstance) {
        super.onCreate(savedInstance);
        userType = (TipoAccount)getIntent().getSerializableExtra("TIPO");
        setContentView(R.layout.activity_edit_profile);
        updateButton = findViewById(R.id.profileEdit_updateBtton);
        imagePreview = findViewById(R.id.imageView2);

        imagePreview.setImageResource(R.drawable.image_user_default);
        LoadProfile();
        registerResult();

        updateButton.setOnClickListener(view -> {
            try {
                UpdateProfile();
                goToHomeActivity();
            } catch (JsonProcessingException | InterruptedException e) {
                Toast.makeText(this, "Impossible aggiornare il Profilo",Toast.LENGTH_LONG).show();
                throw new RuntimeException(e);
            } catch (NonameException e){
                Toast.makeText(this, "Inserire il nome per inizializzare l Account",Toast.LENGTH_LONG).show();
            }
        });
        imagePreview.setOnClickListener(view -> {
            imageChooser();
        });
    }

    private void goToHomeActivity() {
        Logger.log("EditProfilePage","goToHomeActivity");
        Intent myIntent = new Intent(EditProfileActivity.this, HomeActivity.class);
        if(userType != null){
            myIntent.putExtra("TIPO",userType);
        }
        EditProfileActivity.this.startActivity(myIntent);
        finish();
    }


    //TODO c'è il problema nell update json non riesce a convertire l utente
    private void UpdateProfile() throws JsonProcessingException, InterruptedException, NonameException {
        String name = ((EditText)findViewById(R.id.profileEdit_name)).getText().toString();
        String surname = ((EditText)findViewById(R.id.profileEdit_surname)).getText().toString();
        String bio = ((EditText)findViewById(R.id.profileEdit_bio)).getText().toString();
        Drawable drawable = imagePreview.getDrawable();
        if(drawable != null) {
            BitmapDrawable bitmapDrawable = ((BitmapDrawable) drawable);
            imageBitmap = bitmapDrawable.getBitmap();
        }
        if(!name.isEmpty()){
            userProfileController.UpdateUserProfile(name,surname,bio,imageBitmap);
        }else {
            throw new NonameException();
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

    void imageChooser() {
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        resultLauncher.launch(intent);
    }

    void registerResult() {
        resultLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                new ActivityResultCallback<ActivityResult>() {
                    @Override
                    public void onActivityResult(ActivityResult result) {
                        try {
                            Uri imageUri = result.getData().getData();
                            imagePreview.setImageURI(imageUri);
                            imagePreview.setTag(imageUri.toString());
                        }
                        catch (Exception e) {
                            Toast.makeText(EditProfileActivity.this, "Nessun immagine selezionata", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    //UTILITY VARIE LOCALI
    private class NonameException extends Throwable {
    }
}
