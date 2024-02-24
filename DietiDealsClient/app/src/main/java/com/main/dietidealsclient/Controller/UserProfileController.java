package com.main.dietidealsclient.Controller;

import com.main.dietidealsclient.Model.Utente;
import com.main.dietidealsclient.Requesters.UtentiRequester;

/** Or Controller directly? */
public class UserProfileController {

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

    public void Login(String email, String password) throws InterruptedException {
        utentiRequester.jwtLogin(email,password);
    }

    public void Register(String email, String password) throws InterruptedException {
//        utentiRequester.jwtRegister(email,password);
    }

    public void UpdateUserProfile(){
        //C'è un logged user?
//        utentiRequester.updateUtente();
    }

}
