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
                       @JsonProperty("minOffer") Float minOffer) {
        super(id, scadenza, nomeProdotto, descrizione, cat, img, scaduta);
        this.minOffer = minOffer;
    }
}