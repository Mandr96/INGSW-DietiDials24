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
                Toast.makeText(RegisterActivity.this, "Registrazione non riuscita",Toast.LENGTH_LONG).show();
            }
        });

        findViewById(R.id.login_cancelButton).setOnClickListener(view -> goToLoginActivity());
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
        Intent myIntent = new Intent(RegisterActivity.this, HomeActivity.class);
        RegisterActivity.this.startActivity(myIntent);
        finish();
    }

    private void goToLoginActivity(){
        Intent myIntent = new Intent(RegisterActivity.this, LoginActivity.class);
        RegisterActivity.this.startActivity(myIntent);
        finish();
    }
}
