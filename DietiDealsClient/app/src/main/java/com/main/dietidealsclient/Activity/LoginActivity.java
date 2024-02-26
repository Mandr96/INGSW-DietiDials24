package com.main.dietidealsclient.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.activity.ComponentActivity;

import com.main.dietidealsclient.Controller.UserProfileController;
import com.main.dietidealsclient.MainActivity;
import com.main.dietidealsclient.R;

import javax.security.auth.login.LoginException;

public class LoginActivity extends ComponentActivity {

    UserProfileController userProfileController;
    Button buttonLogin, buttonRegister;
    TextView errorText;

    public LoginActivity() {
        userProfileController = UserProfileController.getInstance();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        buttonLogin = findViewById(R.id.loginButton);
        buttonRegister = findViewById(R.id.gotoRegisterButton);
        errorText = findViewById(R.id.login_errorText);

        View.OnClickListener loginOnclickListener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(TryLogin()){
                    goToHomeActivity();
                }
            }
        };

        View.OnClickListener registerOnClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                goToRegisterActivity();
            }
        };

        buttonLogin.setOnClickListener(loginOnclickListener);
        buttonRegister.setOnClickListener(registerOnClickListener);
    }

    private boolean TryLogin() {
        String email = ((EditText)findViewById(R.id.login_editTextEmail)).getText().toString();
        String password = ((EditText)findViewById(R.id.login_editTextPassword)).getText().toString();

        if(!password.isEmpty() || !email.isEmpty()){
            try {
                userProfileController.Login(email,password);
                return true;
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            } catch (LoginException e) {
                errorText.setText("Email o Password sbagliata");
            }
        }
        return false;
    }


    private void goToHomeActivity(){
        //TODO attualmente va alla main act che sarebbero i test di @gianmarco
        Intent myIntent = new Intent(LoginActivity.this, HomeCompratoreActivity.class);
        LoginActivity.this.startActivity(myIntent);
        finish();
    }

    private void goToRegisterActivity(){
        Intent myIntent = new Intent(LoginActivity.this, RegisterActivity.class);
        LoginActivity.this.startActivity(myIntent);
        finish();
    }
}

