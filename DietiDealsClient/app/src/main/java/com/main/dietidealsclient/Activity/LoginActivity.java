package com.main.dietidealsclient.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.ComponentActivity;

import com.main.dietidealsclient.Controller.UserProfileController;
import com.main.dietidealsclient.MainTest;
import com.main.dietidealsclient.R;

import javax.security.auth.login.LoginException;

public class LoginActivity extends ComponentActivity {

    UserProfileController userProfileController;
    Button buttonLogin, buttonRegister;
    TextView errorText;

    public LoginActivity() {
        userProfileController = new UserProfileController();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

//        MainTest.start();

        buttonLogin = findViewById(R.id.loginButton);
        buttonRegister = findViewById(R.id.gotoRegisterButton);
        errorText = findViewById(R.id.login_errorText);

        buttonLogin.setOnClickListener(view -> {
            if(TryLogin()){
                goToHomeActivity();
            } else {
                Toast.makeText(LoginActivity.this, "Login non riuscito",Toast.LENGTH_LONG).show();
            }
        });

        buttonRegister.setOnClickListener(view -> goToRegisterActivity());
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

