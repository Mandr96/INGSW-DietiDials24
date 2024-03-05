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

    public enum tipoHomepage{
        VENDITORE,
        COMPRATORE
    }
    /** Restituisce le aste dell utente loggato
     * @param tipo (venditore - compratore)
     * @return lista di aste
     */
    public List<Asta> getAsteUtente(tipoHomepage tipo){
        List<Asta> aste = new ArrayList<Asta>();
        for (Asta asta : LoggedUser.getInstance().getLoggedUser().getAste()){
            if(tipo.equals(tipoHomepage.VENDITORE) && asta.getClass().equals(AstaInversa.class)){
                aste.add(asta);
            } else if (tipo.equals(tipoHomepage.COMPRATORE) && (asta.getClass().equals(AstaClassica.class) || asta.getClass().equals(AstaSilenziosa.class))){
                aste.add(asta);
            }
        }
        return aste;
    }

    //TODO Da sistemare (page)
    //Senno possiamo fare che tipo dici da che id mandare? e tipo ne manda 15? (brutto)
    //PU♫O fare due richieste e mettrle insieme
    public List<Asta> ricerca(String keyword, String categoria, String tipoAsta, int page) throws InterruptedException {
        return asteRequester.cercaAsta(tipoAsta,categoria,keyword,page);

    }

    public List<Offerta> getOfferteUtente() throws InterruptedException {
//        asteRequester.getOfferteByUser(LoggedUser.getInstance().getLoggedUser().getEmail());
//        LoggedUser.getInstance().getLoggedUser().getOfferte();
        return asteRequester.getOfferteByUser(LoggedUser.getInstance().getLoggedUser().getEmail());
    }

    public List<Asta> getAstePartecipateDaUtente() throws InterruptedException {
//        List<Asta> ret = new ArrayList<>();
//        List<Offerta> offerte = asteRequester.getOfferteByUser(LoggedUser.getInstance().getLoggedUser().getEmail());
//        Log.e("AsteController - getAstePartecipateDaUtente()","Off" + offerte.get(0).getOwnerEmail());
//
//        for (Offerta off : offerte){
//            Asta astaTmp = getAstaById(off.getAstaID());
//            ret.add(astaTmp);
//        }
//        List<Asta> ret = asteRequester.getAstePartecipate();
        List<Asta> ret = getAsteUtente(tipoHomepage.COMPRATORE);
//        for (Asta asta : ret){
//            List<Offerta> t = new ArrayList<>();
//            t.add(asta.getBestOffer());
//            asta.setOfferte(t);
//        }
        for (Asta asta : ret){
            Log.e("AsteController - getAstePartecipateDaUtente()","Asta :" + asta);
            asta.setOfferte(asteRequester.getOfferteByAsta(asta.getId()));
            List<Offerta> offerte = new ArrayList<>();
            if(!(asta.getOfferte() == null)){
                offerte.add(asta.getBestOffer());
            }
            asta.setOfferte(offerte);
        }
        return ret;
    }
}
