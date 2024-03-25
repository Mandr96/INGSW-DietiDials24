package com.main.dietidealsclient.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.ComponentActivity;

import com.main.dietidealsclient.Controller.UserProfileController;
import com.main.dietidealsclient.R;
import com.main.dietidealsclient.Utility.Logger;

import javax.security.auth.login.LoginException;

public class RegisterActivity extends ComponentActivity {
    UserProfileController userProfileController;
    TextView errorTxt;

    public RegisterActivity() {
        userProfileController = new UserProfileController();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        errorTxt = findViewById(R.id.register_errorTxt);

        findViewById(R.id.register_button).setOnClickListener(view -> {
            if(TryRegistration()){
                goToHomeActivity();
            }else {
                Toast.makeText(RegisterActivity.this, getString(R.string.registrazione_non_riuscita) ,Toast.LENGTH_LONG).show();
            }
        });

        findViewById(R.id.login_cancelButton).setOnClickListener(view -> goToLoginActivity());
    }

    private boolean TryRegistration() {
        Logger.log("RegisterPage","TryRegistration");
        String email = ((EditText)findViewById(R.id.register_editTextEmail)).getText().toString();
        String passw = ((EditText)findViewById(R.id.register_editTextPassword)).getText().toString();
        String passwR = ((EditText)findViewById(R.id.register_editTextPasswordRe)).getText().toString();

        if(isValidCred(email,passw,passwR)){
            try {
                userProfileController.Register(email,passw);
                Logger.log("RegisterPage","RegistrationSuccess");
                return true;
            } catch (LoginException e) {
                Logger.log("RegisterPage","RegistrationError");
                errorTxt.setText(R.string.errore_e_mail_gi_registrata);
            }
            catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
        Logger.log("RegisterPage","RegistrationError");
        return false;
    }

    /** Controlla se il pattern dell email inserita è quello di un email*/
    public boolean isValidCred(String email,String passw,String passwR) {
        if(!email.isEmpty() && !passw.isEmpty() && !passwR.isEmpty()) {
            if (passw.equals(passwR)) {
                Log.e("EMAIL","matchers dice = " + Patterns.EMAIL_ADDRESS.matcher(email).matches());
                return Patterns.EMAIL_ADDRESS.matcher(email).matches();
            }
        }
        return false;
    }

    private void goToHomeActivity(){
        Logger.log("RegisterPage","goToHomeActivity");
        Intent myIntent = new Intent(RegisterActivity.this, HomeActivity.class);
        RegisterActivity.this.startActivity(myIntent);
        finish();
    }

    private void goToLoginActivity(){
        Logger.log("RegisterPage","goToLoginActivity");
        Intent myIntent = new Intent(RegisterActivity.this, LoginActivity.class);
        RegisterActivity.this.startActivity(myIntent);
        finish();
    }
}
