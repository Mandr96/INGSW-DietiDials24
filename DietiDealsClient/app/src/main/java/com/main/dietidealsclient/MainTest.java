package com.main.dietidealsclient;

import android.util.Log;

import com.main.dietidealsclient.Model.Asta;
import com.main.dietidealsclient.Model.AstaInversa;
import com.main.dietidealsclient.Model.Notifica;
import com.main.dietidealsclient.Model.Offerta;
import com.main.dietidealsclient.Model.Utente;
import com.main.dietidealsclient.Requesters.AsteRequester;
import com.main.dietidealsclient.Requesters.RequestUtility;
import com.main.dietidealsclient.Requesters.UtentiRequester;

import java.io.IOException;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.security.auth.login.LoginException;

import okhttp3.Response;


public class MainTest {

    public static void start() {
        UtentiRequester userRequester = new UtentiRequester();
        AsteRequester asteRequester = new AsteRequester();

        try {
            //requester.signup("gianm","gianmarco", "lembo", "password", "...", "Capri", null,
                    //new ArrayList<Notifica>(), new ArrayList<Asta>(), new ArrayList<Offerta>());
            userRequester.jwtLogin("prova", "1234");
            Utente loggedUser = userRequester.getUtenteByEmail("prova");
            Asta retrievedAsta = asteRequester.getAstaById(1L);
            List<Asta> aste = asteRequester.cercaAsta("AstaSilenziosa", "any", "cosa", 0);
            List<Offerta> offerte = asteRequester.getOfferteByUser(loggedUser.getEmail());
            offerte = asteRequester.getOfferteByAsta(retrievedAsta.getId());
            Log.d("myDebug", "ID nuova asta: "+asteRequester.inserisciAsta(new AstaInversa(-2L, null, "Grandissima cosa", "...", "cat1", null, false, loggedUser, 20.0f)));
            Log.d("myDebug", "ID nuova offerta: "+asteRequester.inviaOfferta(new Offerta(-2L,50.0f, null, true, loggedUser, retrievedAsta)));
        } catch (InterruptedException | LoginException e) {
            throw new RuntimeException(e);
        }
    }
}


