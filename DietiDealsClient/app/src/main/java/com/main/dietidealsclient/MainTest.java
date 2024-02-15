package com.main.dietidealsclient;

import android.util.Log;

import com.main.dietidealsclient.Model.Asta;
import com.main.dietidealsclient.Model.Notifica;
import com.main.dietidealsclient.Model.Offerta;
import com.main.dietidealsclient.Model.Utente;
import com.main.dietidealsclient.Requesters.RequestUtility;
import com.main.dietidealsclient.Requesters.UtentiRequester;

import java.io.IOException;
import java.util.ArrayList;

import okhttp3.Response;


public class MainTest {

    public static void start() {
        UtentiRequester requester = new UtentiRequester();

        try {
            requester.signup("gianm","gianmarco", "lembo", "password", "...", "Capri", null,
                    new ArrayList<Notifica>(), new ArrayList<Asta>(), new ArrayList<Offerta>());
            requester.jwtLogin("prova", "1234");
            Utente loggedUser = requester.getUtenteByEmail("prova");
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}


