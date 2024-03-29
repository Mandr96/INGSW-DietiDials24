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
import com.main.dietidealsclient.Utility.Logger;

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

        //MainTest.start();

        Logger.log("LoginPage","started");

        EditText em = findViewById(R.id.login_editTextEmail);
        EditText pw = findViewById(R.id.login_editTextPassword);
        em.setText("prova");
        pw.setText("1234");
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
        Logger.log("LoginPage","TryLogin");
        String email = ((EditText)findViewById(R.id.login_editTextEmail)).getText().toString();
        String password = ((EditText)findViewById(R.id.login_editTextPassword)).getText().toString();

        try {
            userProfileController.Login(email,password);
            Logger.log("LoginPage","LoginSuccess");
            return true;
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        } catch (LoginException e) {
            Logger.log("LoginPage","LoginException");
            errorText.setText(R.string.email_o_password_sbagliata);
        }
        return false;
}


    private void goToHomeActivity(){
        Intent myIntent = new Intent(LoginActivity.this, HomeActivity.class);
        LoginActivity.this.startActivity(myIntent);
        finish();
    }

    private void goToRegisterActivity(){
        Logger.log("LoginPage","goToRegisterActivity");
        Intent myIntent = new Intent(LoginActivity.this, RegisterActivity.class);
        LoginActivity.this.startActivity(myIntent);
        finish();
    }
}

