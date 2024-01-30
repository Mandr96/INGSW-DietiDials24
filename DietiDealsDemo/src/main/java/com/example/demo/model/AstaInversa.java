package com.example.demo.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.Entity;
import lombok.NoArgsConstructor;

import java.io.File;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

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
        Offerta bestOffer = offerte.get(0);
        for(Offerta offer : offerte) {
            if (offer.getValore().compareTo(bestOffer.getValore()) < 0) {
                bestOffer = offer;
                notifiche.add(new Notifica("Asta scaduta!", "L'asta per " + getNomeProdotto() +
                        " a cui partecipi è scaduta, ti sei aggiudicato l'articolo!", false, offer.getOwner()));
            } else {
                notifiche.add(new Notifica("Asta scaduta!", "L'asta per " + getNomeProdotto() +
                        " a cui partecipi è scaduta. La tua offerta non è stata la migliore!", false, offer.getOwner()));
            }
        }
        return notifiche;
    }
}
