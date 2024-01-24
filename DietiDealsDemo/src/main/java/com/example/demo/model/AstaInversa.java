package com.example.demo.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.Entity;
import lombok.NoArgsConstructor;

import java.io.File;
import java.sql.Timestamp;
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

    public Offerta chiudi() {
        Offerta bestOffer = offerte.get(0);
        for(Offerta offer : offerte) {
            if(offer.getValore().compareTo(bestOffer.getValore()) < 0)
                bestOffer = offer;
        }
        return bestOffer;
    }
}
