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
public class AstaSilenziosa extends Asta{

    @JsonCreator
    public AstaSilenziosa(@JsonProperty("id") Long id,
                          @JsonProperty("scadenza") Timestamp scadenza,
                          @JsonProperty("nomeProdotto") String nomeProdotto,
                          @JsonProperty("descrizione") String descrizione,
                          @JsonProperty("categoria") String cat,
                          @JsonProperty("img") File img,
                          @JsonProperty("scaduta") Boolean scaduta,
                          @JsonProperty("creatore") Utente creatore) {
        super(id, scadenza, nomeProdotto, descrizione, cat, img, scaduta, creatore);
    }


    public AstaSilenziosa(Timestamp scadenza,
                          String nomeProdotto,
                          String descrizione,
                          String cat,
                          File img,
                          Utente creatore) {
        super(scadenza, nomeProdotto, descrizione, cat, img, creatore);
    }

    public Offerta getBestOffer(){
        return getBestOfferIn(true);
    }

    public String getTypeAsString() {
        return "Asta Silenziosa";
    }

    @Override
    public Float getActualPrice() {
        return -1F;
    }
}
