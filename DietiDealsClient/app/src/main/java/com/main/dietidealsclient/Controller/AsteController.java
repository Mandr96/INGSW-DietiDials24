package com.main.dietidealsclient.Controller;

import android.util.Log;

import com.main.dietidealsclient.Model.Asta;
import com.main.dietidealsclient.Model.AstaClassica;
import com.main.dietidealsclient.Model.AstaInversa;
import com.main.dietidealsclient.Model.AstaSilenziosa;
import com.main.dietidealsclient.Model.Offerta;
import com.main.dietidealsclient.Requesters.AsteRequester;
import com.main.dietidealsclient.Utility.LoggedUser;

import java.io.File;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

public class AsteController {

    private AsteRequester asteRequester;

    public AsteController(){
        asteRequester = new AsteRequester();
    }

    public Asta getAstaById(Long id) throws InterruptedException {
        return asteRequester.getAstaById(id);
    }

    public Long createNewAsta(Timestamp scadenza, String name, String description, String cat, File img, String type, Float minPrice) throws InterruptedException {
        Asta asta = null;
        if(type.equals("Classica")){
            asta = new AstaClassica(scadenza, name, description, cat, img, LoggedUser.getInstance().getLoggedUser(), minPrice);
        } else if(type.equals("Silenziosa")){
            asta = new AstaSilenziosa(scadenza, name, description, cat, img, LoggedUser.getInstance().getLoggedUser());
        } else if (type.equals("Inversa")){
            asta = new AstaInversa(scadenza, name, description, cat, img, LoggedUser.getInstance().getLoggedUser(), minPrice);
        }

        return asteRequester.inserisciAsta(asta);
    }

    /** Restituisce le aste dell utente loggato
     * @param tipo (venditore - compratore)
     * @return lista di aste
     */
    public List<Asta> getAsteUtente(TipoAccount tipo){
        List<Asta> aste = new ArrayList<Asta>();
        for (Asta asta : LoggedUser.getInstance().getLoggedUser().getAste()){
            if(tipo.equals(TipoAccount.COMPRATORE) && asta instanceof AstaInversa){
                aste.add(asta);
            } else if (tipo.equals(TipoAccount.VENDITORE) && (asta.getClass().equals(AstaClassica.class) || asta.getClass().equals(AstaSilenziosa.class))){
                aste.add(asta);
            }
        }
        Log.d("myDebug getAsteUtente" , aste.toString());
        return aste;
    }

    //TODO Da sistemare (page)
    //Senno possiamo fare che tipo dici da che id mandare? e tipo ne manda 15? (brutto)
    //PU♫O fare due richieste e mettrle insieme
    public List<Asta> ricerca(String keyword, String categoria, String tipoAsta, int page, TipoAccount userType)
    {
        List<Asta> results = null;
        try {results = asteRequester.cercaAsta(tipoAsta,categoria,keyword,page);} catch (InterruptedException e) {throw new RuntimeException(e);}
        if(userType.equals(TipoAccount.COMPRATORE)){
            results.removeIf(asta -> asta instanceof AstaInversa);
        }
        return results;
    }

//    public List<Offerta> getOfferteUtente() throws InterruptedException {
////        asteRequester.getOfferteByUser(LoggedUser.getInstance().getLoggedUser().getEmail());
////        LoggedUser.getInstance().getLoggedUser().getOfferte();
//        return asteRequester.getOfferteByUser(LoggedUser.getInstance().getLoggedUser().getEmail());
//    }

    public List<Asta> getAstePartecipate(TipoAccount userType){
        List<Asta> aste = null;
        try { aste = asteRequester.getAstePartecipate();} catch (InterruptedException e) {throw new RuntimeException(e);}
        if (userType.equals(TipoAccount.VENDITORE)){
            // SOLO ASTE INVERSE
            aste.removeIf(asta -> !(asta instanceof AstaInversa));

        } else if (userType.equals(TipoAccount.COMPRATORE)){
            // RIMOZIONE ASTE INVERSE
            aste.removeIf(asta -> asta instanceof AstaInversa);
        }
        // RECUPERO OFFERTE
        for (Asta asta : aste) {
            try {asta.setOfferte(asteRequester.getOfferteByAsta(asta.getId()));} catch (InterruptedException e) {throw new RuntimeException(e);}
            Log.d("MyDebug getAstePartecipate" , "l asta " + asta.getId() + " ha le offerte " + asta.getOfferte().toString());
        }
        return aste;
    }

    /*
    public List<Asta> getAstePartecipateDaCompratore() {
        List<Asta> aste = null;
        try { aste = asteRequester.getAstePartecipate();} catch (InterruptedException e) {throw new RuntimeException(e);}
        // RIMOZIONE ASTE INVERSE
        for(Asta asta : aste) {
            if(asta instanceof AstaInversa)
                aste.remove(asta);
        }
        // RECUPERO OFFERTE
        for (Asta asta : aste) {
            try {asta.setOfferte(asteRequester.getOfferteByAsta(asta.getId()));} catch (InterruptedException e) {throw new RuntimeException(e);}
        }
        return aste;
    }
    */
}
