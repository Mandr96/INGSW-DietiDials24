package com.main.dietidealsclient.Controller;

import android.graphics.Bitmap;
import android.util.Log;

import com.main.dietidealsclient.Model.Asta;
import com.main.dietidealsclient.Model.AstaClassica;
import com.main.dietidealsclient.Model.AstaInversa;
import com.main.dietidealsclient.Model.AstaSilenziosa;
import com.main.dietidealsclient.Model.Offerta;
import com.main.dietidealsclient.Requesters.AsteRequester;
import com.main.dietidealsclient.Utility.LoggedUser;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
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

    public void createNewAsta(Timestamp scadenza, String name, String description, String cat, File fileImg, String type, Float minPrice) throws InterruptedException {
        Asta asta = null;
        Long astaID = -1L;
        if(type.equals("Classica")){
            asta = new AstaClassica(scadenza, name, description, cat, null, LoggedUser.getInstance().getLoggedUser(), minPrice);
        } else if(type.equals("Silenziosa")){
            asta = new AstaSilenziosa(scadenza, name, description, cat, null, LoggedUser.getInstance().getLoggedUser());
        } else if (type.equals("Inversa")){
            asta = new AstaInversa(scadenza, name, description, cat, null, LoggedUser.getInstance().getLoggedUser(), minPrice);
        }
        astaID = asteRequester.inserisciAsta(asta);
        if(astaID != -1L && fileImg != null) {
            Log.d("myDebug", "Preparando l'immagine...");
            //TODO buggato madonna
            //asteRequester.setAstaImg(astaID, fileImg);
        }
    }

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

    public void createNewOffer(Asta asta, Float valore) {
        try {
            Log.d("myDebug", "OK3.5");
            Offerta offer = new Offerta(-1L, valore, null, true, LoggedUser.getInstance().getLoggedUser(), asta);
            offer.setId(asteRequester.inviaOfferta(offer));
            Log.d("myDebug", "OK3.6");
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
