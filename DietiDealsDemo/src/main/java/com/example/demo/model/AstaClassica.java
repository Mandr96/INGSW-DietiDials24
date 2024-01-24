package com.example.demo.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.File;
import java.sql.Timestamp;
import java.util.List;

@NoArgsConstructor
@Entity
public class AstaClassica extends Asta{
    private Float minPrice;

    public AstaClassica(Long id, Timestamp scadenza, String nomeProdotto, String descrizione, String cat, File img, Boolean scaduta, Utente creatore, List<Offerta> offerte,
                        Float minPrice) {
        super(id, scadenza, nomeProdotto, descrizione, cat, img, scaduta, creatore, offerte);
        this.minPrice = minPrice;
    }

    public Offerta chiudi() {
        Offerta bestOffer = offerte.get(0);
        for(Offerta offer : offerte) {
            if(offer.getValore().compareTo(bestOffer.getValore()) > 0)
                bestOffer = offer;
        }
        return bestOffer;
    }
}
