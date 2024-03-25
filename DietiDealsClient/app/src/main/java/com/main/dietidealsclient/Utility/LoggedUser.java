package com.main.dietidealsclient.Utility;
import android.util.Log;

import com.main.dietidealsclient.Model.Utente;
import com.main.dietidealsclient.Requesters.UtentiRequester;

public class LoggedUser {
    private static LoggedUser instance;
    private Utente loggedUser;

    private LoggedUser(){
        loggedUser = null;
    }

    public static LoggedUser getInstance(){
        if(instance == null){
            Log.d("MyDebug","INSTANZIATO LOGGED USER");
            instance = new LoggedUser();
        }
        return instance;
    }

    public static void update() {
        UtentiRequester utentiRequester = new UtentiRequester();
        try {
            instance.setLoggedUser(utentiRequester.getUtenteByEmail(instance.getLoggedUser().getEmail()));
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    public void setLoggedUser(Utente user){
        Log.d("MyDebug","Settato loggedUSer : " + user.getEmail());
        loggedUser = user;
    }

    public Utente getLoggedUser(){
        return loggedUser;
    }
}
