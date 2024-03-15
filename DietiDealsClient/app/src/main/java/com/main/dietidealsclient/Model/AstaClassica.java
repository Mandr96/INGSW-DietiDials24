package com.main.dietidealsclient.Model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.File;
import java.sql.Timestamp;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class AstaClassica extends Asta {
    private Float minPrice;

    @JsonCreator
    public AstaClassica(@JsonProperty("id") Long id,
                        @JsonProperty("scadenza") Timestamp scadenza,
                        @JsonProperty("nomeProdotto") String nomeProdotto,
                        @JsonProperty("descrizione") String descrizione,
                        @JsonProperty("categoria") String cat,
                        @JsonProperty("img") File img,
                        @JsonProperty("scaduta") Boolean scaduta,
                        @JsonProperty("creatore") Utente creatore,
                        @JsonProperty("minPrice") Float minPrice) {
        super(id, scadenza, nomeProdotto, descrizione, cat, img, scaduta, creatore);
        this.minPrice = minPrice;
    }

    public AstaClassica(Timestamp scadenza,
                        String nomeProdotto,
                        String descrizione,
                        String cat,
                        File img,
                        Utente creatore,
                        Float minPrice) {
        super(scadenza, nomeProdotto, descrizione, cat, img, creatore);
        this.minPrice = minPrice;
    }

    @JsonIgnore
    public Offerta getBestOffer(){
        return getBestOfferIn(true);
    }

    public Float getMinPrice() {
        return minPrice;
    }

    public String getMinPriceAsString() {
        NumberFormat formatter = new DecimalFormat("0.00");
        return formatter.format(minPrice)+" €";
    }

    public String getTypeAsString() {
        return "Asta Classica";
    }

    @Override
    public Float getActualPrice() {
        return minPrice;
    }
}
