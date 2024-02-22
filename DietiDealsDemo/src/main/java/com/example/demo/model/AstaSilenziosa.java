package com.example.demo.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import jakarta.persistence.Entity;
import lombok.NoArgsConstructor;

import java.io.File;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@Entity
public class AstaSilenziosa extends Asta{

    public AstaSilenziosa(Long id, Timestamp scadenza, String nomeProdotto, String descrizione, String cat, File img, Boolean scaduta, Utente creatore, List<Offerta> offerte) {
        super(id, scadenza, nomeProdotto, descrizione, cat, img, scaduta, creatore, offerte);
    }

    public List<Notifica> chiudi() {
        List<Notifica> notifiche = new ArrayList<Notifica>();
        if(!offerte.isEmpty()){
            notifiche.add(new Notifica("La tua asta è terminata","Vieni a scegliere chi si aggiudicherà " + getNomeProdotto(), false , getCreatore()));
        }else {
            notifiche.add(new Notifica("La tua asta è terminata","Non hai ricevuto nessun offerta per " + getNomeProdotto(), false , getCreatore()));

        }
        return notifiche;
    }

    public List<Notifica> accetta() { return new ArrayList<Notifica>(); }
}
