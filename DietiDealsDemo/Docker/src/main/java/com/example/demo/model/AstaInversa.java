package com.example.demo.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.File;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
@Getter
@Setter
@NoArgsConstructor
@Entity
public class AstaInversa extends Asta {
    private Float minOffer;


    public AstaInversa(Long id, Timestamp scadenza, String nomeProdotto, String descrizione, String cat, File img, Boolean scaduta, Utente creatore, List<Offerta> offerte,
                       Float minOffer) {
        super(id, scadenza, nomeProdotto, descrizione, cat, img, scaduta, creatore, offerte);
        this.minOffer = minOffer;
    }

    public List<Notifica> chiudi() {
        List<Notifica> notifiche = new ArrayList<Notifica>();
        if(!offerte.isEmpty()){
            Offerta bestOffer = getBestOffer();
            for(Offerta offer : offerte) {
                if (offer.getValore().compareTo(bestOffer.getValore()) < 0) {
                    bestOffer = offer;
                    Utente winner = offer.getOwner();
                    //Notifica vincitore
                    notifiche.add(new Notifica("Asta inversa scaduta!", "L'asta per " + getNomeProdotto() +
                            " a cui partecipi è scaduta, ti sei aggiudicato la vendita!", false, winner));
                    //Notifica creatore asta
                    notifiche.add(new Notifica("La tua asta inversa è scaduta", winner.getNome()+" "+winner.getCognome()+
                            " si è aggiudicato la vendita di "+nomeProdotto, false, creatore));
                } else {
                    //Notifiche altri utenti
                    notifiche.add(new Notifica("Asta inversa scaduta!", "L'asta per " + getNomeProdotto() +
                            " a cui partecipi è scaduta. La tua offerta non è stata la migliore", false, offer.getOwner()));
                }
            }
        }
        else {
            //Notifica creatore asta in caso di nessuna offerta
            notifiche.add(new Notifica("La tua asta inversa è scaduta!", "Non hai ricevuto nessuna offerta per "+nomeProdotto, false, creatore));
        }
        return notifiche;
    }

    public Offerta getBestOffer(){
        return getBestOfferIn(false);
    }
}
