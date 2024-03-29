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
        //em.setText("");
        //pw.setText("");
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

        em.setOnFocusChangeListener((view, isFocused) -> {
            if(isFocused)
                Logger.log("LoginPage", "Inizio inserimento email");
            else
                Logger.log("LoginPage", "Fine inserimento email");
        });
        pw.setOnFocusChangeListener((view, isFocused) -> {
            if(isFocused)
                Logger.log("LoginPage", "Inizio inserimento password");
            else
                Logger.log("LoginPage", "Fine inserimento password");
        });
    }

    private boolean TryLogin() {
        Logger.log("LoginPage","Invio richiesta di login");
        String email = ((EditText)findViewById(R.id.login_editTextEmail)).getText().toString();
        String password = ((EditText)findViewById(R.id.login_editTextPassword)).getText().toString();

        try {
            userProfileController.Login(email,password);
            Logger.log("LoginPage","Login effettuato");
            return true;
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        } catch (LoginException e) {
            Logger.log("LoginPage","Errore di Login");
            errorText.setText(R.string.email_o_password_sbagliata);
        }
        return false;
}


    private void goToHomeActivity(){
        Logger.log("LoginPage","Cambio Activity -> HomePage");
        Intent myIntent = new Intent(LoginActivity.this, HomeActivity.class);
        LoginActivity.this.startActivity(myIntent);
        finish();
    }

    private void goToRegisterActivity(){
        Logger.log("LoginPage","Cambio Activity -> RegisterPage");
        Intent myIntent = new Intent(LoginActivity.this, RegisterActivity.class);
        LoginActivity.this.startActivity(myIntent);
        finish();
    }
}

