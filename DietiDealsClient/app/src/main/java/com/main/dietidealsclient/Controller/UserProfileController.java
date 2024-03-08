package com.main.dietidealsclient.Controller;

import android.util.Log;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.main.dietidealsclient.Model.Utente;
import com.main.dietidealsclient.Requesters.UtentiRequester;
import com.main.dietidealsclient.Utility.LoggedUser;

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
//        loggedUser = utentiRequester.getUtenteByEmail(email);
    }

    //TODO Da aggiungere link e FOTO
    public void UpdateUserProfile(String name, String surname, String bio) throws JsonProcessingException, InterruptedException {
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

    public Utente getAstaOwner(Long astaID){
        try {
            return utentiRequester.getAstaOwner(astaID);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
