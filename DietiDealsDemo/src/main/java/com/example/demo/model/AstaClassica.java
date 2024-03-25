package com.example.demo.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.File;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
@Getter
@Setter
@NoArgsConstructor
@Entity
public class AstaClassica extends Asta{
    private Float minPrice;

    public AstaClassica(Long id, Timestamp scadenza, String nomeProdotto, String descrizione, String cat, File img, Boolean scaduta, Utente creatore, List<Offerta> offerte,
                        Float minPrice) {
        super(id, scadenza, nomeProdotto, descrizione, cat, img, scaduta, creatore, offerte);
        this.minPrice = minPrice;
    }

    public List<Notifica> chiudi() {
        List<Notifica> notifiche = new ArrayList<Notifica>();
        if(!offerte.isEmpty()){
            Offerta bestOffer = offerte.get(0);
            for(Offerta offer : offerte) {
                if(offer.getValore().compareTo(bestOffer.getValore()) > 0) {
                    //Notifica vincitore asta
                    bestOffer = offer;
                    Utente winner = offer.getOwner();
                    notifiche.add(new Notifica("Asta scaduta!", "L'asta per "+getNomeProdotto()+
                            " a cui partecipi è scaduta, ti sei aggiudicato l'articolo!", false, winner));
                    //Notifica creatore asta
                    notifiche.add(new Notifica("La tua asta è scaduta", winner.getNome()+" "+winner.getCognome()+ " si è aggiudicato "+
                            nomeProdotto, false, creatore));
                }
                else {
                    //Notifiche altri utenti
                    notifiche.add(new Notifica("Asta scaduta!", "L'asta per "+getNomeProdotto()+
                            " a cui partecipi è scaduta. La tua offerta non è stata la migliore", false, offer.getOwner()));
                }
            }
        }
        else {
            //Notifica creatore asta in caso di nessuna offerta
            notifiche.add(new Notifica("La tua asta è scaduta!", "Non hai ricevuto nessuna offerta per "+nomeProdotto, false, creatore));
        }
        return notifiche;
    }
}
