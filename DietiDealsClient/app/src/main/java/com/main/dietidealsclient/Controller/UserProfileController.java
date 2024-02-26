package com.main.dietidealsclient.Controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.main.dietidealsclient.Model.Utente;
import com.main.dietidealsclient.Requesters.UtentiRequester;

import javax.security.auth.login.LoginException;

/** Controller per quato riguarda il login e la gestione del profilo
 * ha anche loggedUser */
public class UserProfileController {

    private Utente loggedUser;
    private static UserProfileController instance;
    UtentiRequester utentiRequester;

    private UserProfileController(){
        utentiRequester = new UtentiRequester();
    }

    public static UserProfileController getInstance(){
        if (instance == null){
            instance = new UserProfileController();
        }
        return instance;
    }

    public void Login(String email, String password) throws InterruptedException, LoginException {
        utentiRequester.jwtLogin(email,password);
        loggedUser = utentiRequester.getUtenteByEmail(email);
    }

    public void Register(String email, String password) throws InterruptedException, LoginException {
        utentiRequester.jwtRegister(email,password);
        loggedUser = utentiRequester.getUtenteByEmail(email);
    }

    //TODO Da aggiungere link e FOTO
    public void UpdateUserProfile(String name, String surname, String bio) throws JsonProcessingException, InterruptedException {
        loggedUser.setNome(name);
        loggedUser.setCognome(surname);
        loggedUser.setShortbio(bio);

        utentiRequester.updateUtente(loggedUser);
    }

    //TODO Serve un logged user credo che così potrebbe adare
    public Utente getLoggedUser(){ return loggedUser; }
//    public void setLoggedUser(Utente loggedUser){ this.loggedUser = loggedUser; }

}
