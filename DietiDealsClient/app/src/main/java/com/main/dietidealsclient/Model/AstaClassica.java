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
                        @JsonProperty("minPrice") Float minPrice) {
        super(id, scadenza, nomeProdotto, descrizione, cat, img, scaduta);
        this.minPrice = minPrice;
    }
}
