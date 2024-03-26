package com.example.demo;

import com.example.demo.model.*;

import java.io.File;
import java.sql.Timestamp;
import java.util.List;

public class AstaTest extends AstaClassica {
    public AstaTest(Long id, Timestamp scadenza, String nomeProdotto, String descrizione, String cat, File img, Boolean scaduta, Utente creatore, List<Offerta> offerte, Float minPrice) {
        super(id, scadenza, nomeProdotto, descrizione, cat, img, scaduta, creatore, offerte, minPrice);
    }

    public Notifica inviaNotifiche(Utente user){
        if(!offerte.isEmpty()){
            Offerta bestOffer = getBestOffer();
            if (bestOffer.getOwner().equals(user) /*&& !bestOffer.getOwner().equals(creatore)*/){
                //Notifica vincitore
                return new NotificaTest("a1", "a1", false, user);
            }
            if (creatore.equals(user) /*&& !bestOffer.getOwner().equals(user)*/){
                //Notifica creatore asta
                return new NotificaTest("a2", "a2", false, user);
            }
            for(Offerta offer : offerte) {
                if (offer.getOwner().equals(user)){
                    //Notifiche altri utenti
                    return new NotificaTest("a3", "a3", false, user);
                }
            }
        } else if (creatore.equals(user)){
            //Notifica creatore asta in caso di nessuna offerta
            return new NotificaTest("a4", "a4", false, user);
        }
        throw new IllegalArgumentException();
    }

}
