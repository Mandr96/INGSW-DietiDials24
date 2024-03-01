package com.main.dietidealsclient.Utility;
import com.main.dietidealsclient.Model.Utente;

public class LoggedUser {
    private static LoggedUser instance;
    private Utente loggedUser;

    private LoggedUser(){
        loggedUser = null;
    }

    public static LoggedUser getInstance(){
        if(instance == null){
            instance = new LoggedUser();
        }
        return instance;
    }

    public void setLoggedUser(Utente user){
        loggedUser = user;
    }

    public Utente getLoggedUser(){
        return loggedUser;
    }
}
