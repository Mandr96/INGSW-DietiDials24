package com.example.demo.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import jakarta.persistence.Entity;
import lombok.NoArgsConstructor;

import java.io.File;
import java.sql.Timestamp;
import java.util.List;

@NoArgsConstructor
@Entity
public class AstaSilenziosa extends Asta{

    public AstaSilenziosa(Long id, Timestamp scadenza, String nomeProdotto, String descrizione, String cat, File img, Boolean scaduta, Utente creatore, List<Offerta> offerte) {
        super(id, scadenza, nomeProdotto, descrizione, cat, img, scaduta, creatore, offerte);
    }

    public Offerta chiudi() {
        return null;
    }
}
