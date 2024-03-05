package com.main.dietidealsclient;

import android.util.Log;

import com.main.dietidealsclient.Controller.AsteController;
import com.main.dietidealsclient.Model.Asta;
import com.main.dietidealsclient.Model.AstaClassica;
import com.main.dietidealsclient.Model.Offerta;
import com.main.dietidealsclient.Requesters.AsteRequester;
import com.main.dietidealsclient.Requesters.UtentiRequester;
import com.main.dietidealsclient.Utility.LoggedUser;

import javax.security.auth.login.LoginException;


public class MainTest {

    public static void start() {
        UtentiRequester userRequester = new UtentiRequester();
        AsteRequester asteRequester = new AsteRequester();

        try {
            //requester.signup("gianm","gianmarco", "lembo", "password", "...", "Capri", null,
                    //new ArrayList<Notifica>(), new ArrayList<Asta>(), new ArrayList<Offerta>());
            userRequester.jwtLogin("prova", "1234");
            LoggedUser.getInstance().setLoggedUser(userRequester.getUtenteByEmail("prova"));

            Asta asta = new AstaClassica(null,"nomeprod","desc","cat1",null,LoggedUser.getInstance().getLoggedUser(),5F);
            asta.setId(asteRequester.inserisciAsta(asta));
            asteRequester.inviaOfferta(new Offerta(-2L,50.0f, null, true, LoggedUser.getInstance().getLoggedUser(), asta));
            Log.d("RES TEST" , "asteController.getAstePartecipateDaCompratore().toString()");
            AsteController asteController = new AsteController();
            Log.d("RES TEST" , asteController.getAstePartecipateDaCompratore().toString());

//            Asta retrievedAsta = asteRequester.getAstaById(1L);
//            List<Asta> aste = asteRequester.cercaAsta("AstaSilenziosa", "any", "cosa", 0);
//            List<Offerta> offerte = asteRequester.getOfferteByUser(loggedUser.getEmail());
//            offerte = asteRequester.getOfferteByAsta(retrievedAsta.getId());
//            Log.d("myDebug", "ID nuova asta: "+asteRequester.inserisciAsta(new AstaInversa(-2L, null, "Grandissima cosa", "...", "cat1", null, false, loggedUser, 20.0f)));
//            Log.d("myDebug", "ID nuova offerta: "+asteRequester.inviaOfferta(new Offerta(-2L,50.0f, null, true, loggedUser, retrievedAsta)));
        } catch (InterruptedException | LoginException e) {
            throw new RuntimeException(e);
        }
    }
}
