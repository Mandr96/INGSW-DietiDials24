package com.main.dietidealsclient.Model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.File;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AstaInversa extends Asta {
    private Float minOffer;

    @JsonCreator
    public AstaInversa(@JsonProperty("id") Long id,
                       @JsonProperty("scadenza") Timestamp scadenza,
                       @JsonProperty("nomeProdotto") String nomeProdotto,
                       @JsonProperty("descrizione") String descrizione,
                       @JsonProperty("categoria") String cat,
                       @JsonProperty("img") File img,
                       @JsonProperty("scaduta") Boolean scaduta,
                       @JsonProperty("creatore") Utente creatore,
                       @JsonProperty("minOffer") Float minOffer) {
        super(id, scadenza, nomeProdotto, descrizione, cat, img, scaduta, creatore);
        this.minOffer = minOffer;
    }


    public AstaInversa(Timestamp scadenza,
                        String nomeProdotto,
                        String descrizione,
                        String cat,
                        File img,
                        Utente creatore,
                        Float minOffer) {
        super(scadenza, nomeProdotto, descrizione, cat, img, creatore);
        this.minOffer = minOffer;
    }

    public Offerta getBestOffer(){
        return getBestOfferIn(false);
    }

    public String getTypeAsString() {
        return "Asta Inversa";
    }
}
