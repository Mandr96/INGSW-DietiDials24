package com.main.dietidealsclient.Controller;

import com.main.dietidealsclient.Model.Asta;
import com.main.dietidealsclient.Requesters.AsteRequester;
import com.main.dietidealsclient.Utility.LoggedUser;

import java.util.List;

public class AsteController {

    private AsteRequester asteRequester;

    public AsteController(){
        asteRequester = new AsteRequester();
    }

    public Asta getAstaById(Long id) throws InterruptedException {
        asteRequester.getAstaById(id);
        return null;
    }

    public List<Asta> getAsteUtente(){
        return LoggedUser.getInstance().getLoggedUser().getAste();
    }

    //TODO Da sistemare (page)
    //Senno possiamo fare che tipo dici da che id mandare? e tipo ne manda 15? (brutto)
    public List<Asta> ricerca(String keyword, String categoria, String tipoAsta, int page) throws InterruptedException {
        return asteRequester.cercaAsta(tipoAsta,categoria,keyword,page);

    }

    public List<Asta> getOfferteUtente() throws InterruptedException {
        asteRequester.getOfferteByUser(LoggedUser.getInstance().getLoggedUser().getEmail());
        return null;
    }
}
