package com.main.dietidealsclient.Controller;

import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.util.Log;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.main.dietidealsclient.Model.Utente;
import com.main.dietidealsclient.Requesters.UtentiRequester;
import com.main.dietidealsclient.Utility.LoggedUser;
import com.main.dietidealsclient.Utility.MyException;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;

import javax.security.auth.login.LoginException;

/** Controller per quato riguarda il login e la gestione del profilo
 * ha loggedUser */
public class UserProfileController {
    UtentiRequester utentiRequester;

    public UserProfileController(){
        utentiRequester = new UtentiRequester();
    }


    public void Login(String email, String password) throws InterruptedException, LoginException {
        utentiRequester.jwtLogin(email,password);
        LoggedUser.getInstance().setLoggedUser(utentiRequester.getUtenteByEmail(email));
//        loggedUser = utentiRequester.getUtenteByEmail(email);
    }

    public void Register(String email, String password) throws InterruptedException, LoginException {
        utentiRequester.jwtRegister(email,password);
        LoggedUser.getInstance().setLoggedUser(utentiRequester.getUtenteByEmail(email));
    }


    public void UpdateUserProfile(String name, String surname, String bio, Bitmap img) throws JsonProcessingException, InterruptedException {
        Utente utente = LoggedUser.getInstance().getLoggedUser();

        utente.setNome(name);
        utente.setCognome(surname);
        utente.setShortbio(bio);

        utentiRequester.updateUtente(new Utente(
                utente.getEmail(),
                utente.getNome(),
                utente.getCognome(),
                utente.getPassword(),
                utente.getShortbio(),
                utente.getCity(),
                utente.getProfilePic(),
                null,
                null,
                null
        ));
        LoggedUser.update();
    }


    public Utente getLoggedUser() {
        return LoggedUser.getInstance().getLoggedUser();
    }

    public Utente getAstaOwner(Long astaID) throws MyException {
        try {
            return utentiRequester.getAstaOwner(astaID);
        } catch (InterruptedException e) {
            throw new MyException(e);
        }
    }

    public Utente getOfferOwner(Long id) {
        try {
            return utentiRequester.getOfferOwner(id);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    public void setUserImg(String useremail, File img) throws MyException {
        try {
            utentiRequester.setUserImg(useremail, img);
        } catch (InterruptedException e) {
            throw new MyException(e);
        }
    }
}
