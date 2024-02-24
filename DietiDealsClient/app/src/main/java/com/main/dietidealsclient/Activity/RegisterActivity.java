package com.main.dietidealsclient.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.activity.ComponentActivity;

import com.main.dietidealsclient.Controller.UserProfileController;
import com.main.dietidealsclient.R;

public class RegisterActivity extends ComponentActivity {
    UserProfileController userProfileController;
    EditText textEmail, textPassword, textPasswordRe;
    Button buttonRegister, buttonCancel;

    public RegisterActivity() {
        userProfileController = UserProfileController.getInstance();
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        buttonRegister = findViewById(R.id.register_button);
        buttonCancel = findViewById(R.id.login_cancelButton);
        textEmail = findViewById(R.id.register_editTextEmail);
        textPassword = findViewById(R.id.register_editTextPassword);
        textPasswordRe = findViewById(R.id.register_editTextPasswordRe);

        View.OnClickListener registerOnClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TryRegistration();
                goToHomeActivity();
            }
        };

        View.OnClickListener cancelOnclickListener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                goToLoginActivity();
            }
        };

        buttonRegister.setOnClickListener(registerOnClickListener);
        buttonCancel.setOnClickListener(cancelOnclickListener);

    }

    private boolean TryRegistration() {
        String email = ((EditText)findViewById(R.id.register_editTextEmail)).getText().toString();
        String passw = ((EditText)findViewById(R.id.register_editTextPassword)).getText().toString();
        String passwR = ((EditText)findViewById(R.id.register_editTextPasswordRe)).getText().toString();

        if(!email.isEmpty() && !passw.isEmpty() && !passwR.isEmpty()){
            if(passw.equals(passwR)){
                try {
                    userProfileController.Register(email,passw);
                    return true;
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        }
        return false;
    }

    /** Controlla se il pattern dell email inserita è quello di un email*/
    public static boolean isValidEmail(CharSequence target) {
        return (!TextUtils.isEmpty(target) && Patterns.EMAIL_ADDRESS.matcher(target).matches());
    }


    private void goToHomeActivity(){
        Intent myIntent = new Intent(RegisterActivity.this, HomeCompratoreActivity.class);
        RegisterActivity.this.startActivity(myIntent);
        finish();
    }

    private void goToLoginActivity(){
        Intent myIntent = new Intent(RegisterActivity.this, LoginActivity.class);
        RegisterActivity.this.startActivity(myIntent);
        finish();
    }
}
