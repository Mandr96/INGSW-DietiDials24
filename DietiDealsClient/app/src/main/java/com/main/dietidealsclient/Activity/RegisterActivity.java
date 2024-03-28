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

    EditText email;
    EditText passw;
    EditText passwR;

    public RegisterActivity() {
        userProfileController = new UserProfileController();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        email = findViewById(R.id.register_editTextEmail);
        passw = findViewById(R.id.register_editTextPassword);
        passwR = findViewById(R.id.register_editTextPasswordRe);

        errorTxt = findViewById(R.id.register_errorTxt);
        findViewById(R.id.register_button).setOnClickListener(view -> {
            if(TryRegistration()){
                goToHomeActivity();
            }else {
                Toast.makeText(RegisterActivity.this, getString(R.string.registrazione_non_riuscita) ,Toast.LENGTH_LONG).show();
            }
        });
        findViewById(R.id.login_cancelButton).setOnClickListener(view -> goToLoginActivity());

        email.setOnFocusChangeListener((view, isFocused) -> {
            if(isFocused)
                Logger.log("RegisterPage", "Inizio inserimento email");
            else
                Logger.log("RegisterPage", "Fine inserimento email");
        });
        passw.setOnFocusChangeListener((view, isFocused) -> {
            if(isFocused)
                Logger.log("RegisterPage", "Inizio inserimento password");
            else
                Logger.log("RegisterPage", "Fine inserimento password");
        });
        passwR.setOnFocusChangeListener((view, isFocused) -> {
            if(isFocused)
                Logger.log("RegisterPage", "Inizio reinserimento password");
            else
                Logger.log("RegisterPage", "Fine reinserimento password");
        });
    }

    private boolean TryRegistration() {
        Logger.log("RegisterPage","Invio richiesta di registrazione inviata");
        String emailText = email.getText().toString();
        String passwText = passw.getText().toString();
        String passwRText = passwR.getText().toString();

        if(isValidCred(emailText,passwText,passwRText)){
            try {
                userProfileController.Register(emailText,passwText);
                Logger.log("RegisterPage","Registrazione effettuata");
                return true;
            } catch (LoginException e) {
                Logger.log("RegisterPage","Errore di registrazione");
                errorTxt.setText(R.string.errore_e_mail_gi_registrata);
            }
            catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
        Logger.log("RegisterPage","Errore di login");
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
        Logger.log("RegisterPage","Cambio Activity -> HomePage");
        Intent myIntent = new Intent(RegisterActivity.this, HomeActivity.class);
        RegisterActivity.this.startActivity(myIntent);
        finish();
    }

    private void goToLoginActivity(){
        Logger.log("RegisterPage","'Indietro' premuto -> LoginPage");
        Intent myIntent = new Intent(RegisterActivity.this, LoginActivity.class);
        RegisterActivity.this.startActivity(myIntent);
        finish();
    }
}
