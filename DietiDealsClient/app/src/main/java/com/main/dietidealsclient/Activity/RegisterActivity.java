package com.main.dietidealsclient.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.activity.ComponentActivity;

import com.main.dietidealsclient.Controller.UserProfileController;
import com.main.dietidealsclient.R;

import javax.security.auth.login.LoginException;

public class RegisterActivity extends ComponentActivity {
    UserProfileController userProfileController;
    Button buttonRegister, buttonCancel;
    TextView errorTxt;

    public RegisterActivity() {
        userProfileController = UserProfileController.getInstance();
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        buttonRegister = findViewById(R.id.register_button);
        buttonCancel = findViewById(R.id.login_cancelButton);
        errorTxt = findViewById(R.id.register_errorTxt);
//        textEmail = findViewById(R.id.register_editTextEmail);
//        textPassword = findViewById(R.id.register_editTextPassword);
//        textPasswordRe = findViewById(R.id.register_editTextPasswordRe);

        View.OnClickListener registerOnClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(TryRegistration())
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

        if(isValidCred(email,passw,passwR)){
            try {
                userProfileController.Register(email,passw);
                return true;
            } catch (LoginException e) {
                errorTxt.setText("Errore E-Mail già registrata");
            }
            catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
        return false;
    }

    /** Controlla se il pattern dell email inserita è quello di un email*/
    public static boolean isValidCred(String email,String passw,String passwR) {
        if(!email.isEmpty() && !passw.isEmpty() && !passwR.isEmpty()) {
            if (passw.equals(passwR)) {
                Log.e("EMAIL","matchers dice = " + Patterns.EMAIL_ADDRESS.matcher(email).matches());
                return Patterns.EMAIL_ADDRESS.matcher(email).matches();
            }
        }
        return false;
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
